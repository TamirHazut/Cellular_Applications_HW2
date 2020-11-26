package com.example.war.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.war.logic.Constants;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public abstract class Fragment_Base extends Fragment {
    private SharedPreferences prefs;
    private Gson gson;
    private MediaPlayer mp;
    private int soundRawId;
    private int mpCurrentPosition;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        prefs = getActivity().getSharedPreferences(Constants.SP_FILE_NAME, getActivity().MODE_PRIVATE);
        gson = new Gson();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            setSoundRawId(savedInstanceState.getInt(Constants.SOUND_RAW_ID));
            setMpCurrentPosition(savedInstanceState.getInt(Constants.SOUND_CURRENT_POSITION));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isMediaPlayerOn()) {
            resumeSound();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isMediaPlayerOn()) {
            pauseSound();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopSound();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.SOUND_RAW_ID, getSoundRawId());
        outState.putInt(Constants.SOUND_CURRENT_POSITION, getMpCurrentPosition());
    }

    protected void saveToSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).apply();
    }

    protected String getFromSharedPreferences(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    protected String toJson(Object o, Type type) {
        if (o == null) {
            return "";
        }
        return this.gson.toJson(o, type);
    }

    protected <T> T fromJson(String json, Type type) {
        return this.gson.fromJson(json, type);
    }

    protected SharedPreferences getPrefs() {
        return prefs;
    }

    protected void playSound(int rawId) {
        if (mp!=null) {
            if (mp.isPlaying()) {
                stopSound();
            }
        }
        this.soundRawId = rawId;
        createMediaPlayer();
        mp.start();
    }

    private void createMediaPlayer() {
        mp = MediaPlayer.create(getActivity(), this.soundRawId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSound();
            }
        });
    }

    protected void pauseSound() {
        mp.pause();
        mpCurrentPosition = mp.getCurrentPosition();
    }

    protected void resumeSound() {
        if (mp == null) {
            createMediaPlayer();
        }
        if (!mp.isPlaying()) {
            mp.seekTo(this.mpCurrentPosition);
            mp.start();
        }
    }

    protected void stopSound() {
        if (mp != null) {
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    protected boolean isMediaPlayerOn() {
        if (this.mp == null) {
            return false;
        }
        return this.mp.isPlaying();
    }

    public int getSoundRawId() {
        return this.soundRawId;
    }

    protected int getMpCurrentPosition() {
        return this.mp.getCurrentPosition();
    }

    public void setSoundRawId(int soundRawId) {
        this.soundRawId = soundRawId;
    }

    public void setMpCurrentPosition(int mpCurrentPosition) {
        this.mpCurrentPosition = mpCurrentPosition;
    }
}

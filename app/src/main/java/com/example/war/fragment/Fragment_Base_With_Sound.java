package com.example.war.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.war.logic.Constants;

public abstract class Fragment_Base_With_Sound extends Fragment_Base {
    private MediaPlayer mp;
    private int soundRawId;
    private int mpCurrentPosition;
    private boolean loop;
    private boolean finished;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            setSoundRawId(savedInstanceState.getInt(Constants.SOUND_RAW_ID));
            setMpCurrentPosition(savedInstanceState.getInt(Constants.SOUND_CURRENT_POSITION));
            setMpLoop(savedInstanceState.getBoolean(Constants.SOUND_LOOP));
        }
        return view;
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
        outState.putBoolean(Constants.SOUND_LOOP, isMediaPlayerLooping());
    }

    protected void playSound(int rawId, boolean loop) {
        if (this.mp!=null) {
            if (this.mp.isPlaying()) {
                stopSound();
            }
        }
        setSoundRawId(rawId);
        setMpLoop(loop);
        createMediaPlayer();
        this.finished = false;
        this.mp.start();
    }

    private void createMediaPlayer() {
        if (getActivity() != null) {
            this.mp = MediaPlayer.create(getActivity(), this.soundRawId);
            this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finished = true;
                    stopSound();
                }
            });
            this.mp.setLooping(loop);
        }
    }

    protected void pauseSound() {
        if (this.mp != null) {
            this.mp.pause();
            this.mpCurrentPosition = this.mp.getCurrentPosition();
        }
    }

    protected void resumeSound() {
        if (this.mp == null) {
            createMediaPlayer();
        }
        if (!this.finished && !this.mp.isPlaying()) {
            this.mp.seekTo(this.mpCurrentPosition);
            this.mp.start();
        }
    }

    protected void stopSound() {
        if (this.mp != null) {
            this.mp.reset();
            this.mp.release();
            this.mp = null;
        }
    }

    protected boolean isMediaPlayerOn() {
        if (this.mp == null) {
            return false;
        }
        return this.mp.isPlaying();
    }

    protected boolean isMediaPlayerLooping() {
        if (this.mp == null) {
            return false;
        }
        return this.mp.isLooping();
    }

    public int getSoundRawId() {
        return this.soundRawId;
    }

    protected int getMpCurrentPosition() {
        return (this.mp != null ? this.mp.getCurrentPosition() : 0);
    }

    public void setSoundRawId(int soundRawId) {
        this.soundRawId = soundRawId;
    }

    public void setMpCurrentPosition(int mpCurrentPosition) {
        this.mpCurrentPosition = mpCurrentPosition;
    }

    public void setMpLoop(boolean loop) {
        this.loop = loop;
    }

}

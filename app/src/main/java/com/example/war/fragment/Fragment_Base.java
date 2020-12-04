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

}

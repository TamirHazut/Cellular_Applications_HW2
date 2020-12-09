package com.example.war;

import android.app.Application;

import com.example.war.logic.SharedPreferencesSingleton;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesSingleton.init(this);
    }

}
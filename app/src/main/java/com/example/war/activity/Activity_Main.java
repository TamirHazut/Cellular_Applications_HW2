package com.example.war.activity;

import androidx.annotation.NonNull;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.war.R;
import com.example.war.fragment.Fragment_Main;
import com.example.war.logic.Constants;
import com.example.war.logic.LocationHandlerSingleton;
import com.example.war.logic.SharedPreferencesSingleton;
import com.example.war.logic.data.entity.Location;
import com.google.gson.Gson;

public class Activity_Main extends Activity_Base {
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        String jsonFromMemory = SharedPreferencesSingleton.getInstance().getPrefs().getString(Constants.LOCATION, "");
        Location playerLocation = gson.fromJson(jsonFromMemory, Location.class);
        if (playerLocation == null) {
            LocationHandlerSingleton.getInstance().getLastLocation(this);
        }
        if (savedInstanceState == null) {
            if (findViewById(R.id.main_FGMT_container) != null) {
                Fragment_Main fragment_main = new Fragment_Main();
                getSupportFragmentManager().beginTransaction().add(R.id.main_FGMT_container, fragment_main).commit();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationHandlerSingleton.getInstance().getLastLocation(this);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationHandlerSingleton.getInstance().validateLocation(this);
    }

}
package com.example.war;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.example.war.fragment.Fragment_Main;
import com.example.war.logic.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class Activity_Main extends Activity_Base {
    private final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private com.example.war.logic.data.entity.Location playerLocation;
    private SharedPreferences prefs;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        prefs = getSharedPreferences(Constants.SP_FILE_NAME, MODE_PRIVATE);
        gson = new Gson();
        String jsonFromMemory = prefs.getString(Constants.LOCATION, "");
        this.playerLocation = gson.fromJson(jsonFromMemory, com.example.war.logic.data.entity.Location.class);
        if (this.playerLocation == null) {
            getLastLocation();
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
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions() && isLocationEnabled()) {
            getLastLocation();
        }
    }
    private void getLastLocation() {
        try {
            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    this.mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
                                playerLocation = new com.example.war.logic.data.entity.Location(location.getLatitude(), location.getLongitude());
                                SharedPreferences.Editor editor = prefs.edit();
                                Gson gson = new Gson();
                                editor.putString(Constants.LOCATION, gson.toJson(playerLocation)).apply();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "This app needs location services", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            } else {
                requestPermissions();
            }
        } catch (SecurityException ex) {
            Toast.makeText(this, "This app needs location services", Toast.LENGTH_LONG).show();
        }
    }

    private void requestNewLocationData() {
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setNumUpdates(1);

            this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            this.mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        } catch (SecurityException ex) {
            requestPermissions();
        }
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            playerLocation = new com.example.war.logic.data.entity.Location(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public com.example.war.logic.data.entity.Location getPlayerLocation() {
        return playerLocation;
    }

}
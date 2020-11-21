package com.example.war;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.war.logic.data.PlayerRepositoryImplementation;
import com.example.war.logic.data.repo.PlayerRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Activity_Main extends AppCompatActivity {
    public static final String LOCATION = "LOCATION";
    private final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private PlayerRepository playersRepository;
    private com.example.war.logic.data.entity.Location playerLocation;
    private Button main_BTN_start;
    private Button main_BTN_top_ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.playersRepository = new PlayerRepositoryImplementation();
        if (savedInstanceState != null) {
            this.playerLocation = (com.example.war.logic.data.entity.Location) savedInstanceState.getSerializable(LOCATION);
        }
        if (this.playerLocation == null) {
            getLastLocation();
        }
        findViews();
        initViews();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LOCATION, this.playerLocation);
    }

    private void findViews() {
        this.main_BTN_start = findViewById(R.id.main_BTN_start);
        this.main_BTN_top_ten = findViewById(R.id.main_BTN_top_ten);
    }

    private void initViews() {
        this.playersRepository.updateTopPlayers();
        this.main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Main.this, Activity_Game.class);
                myIntent.putExtra(LOCATION, playerLocation);
                startActivity(myIntent);
                finish();
            }
        });
        this.main_BTN_top_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Main.this, Activity_Top_Ten.class);
                myIntent.putExtra(Activity_Top_Ten.LIST, playersRepository.findTopPlayers());
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void getLastLocation() {
        try {
            if (isLocationEnabled()) {
                this.mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            playerLocation = new com.example.war.logic.data.entity.Location(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (SecurityException ex) {
            requestPermissions();
            if (checkPermissions()) {
                getLastLocation();
            }
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
        getLastLocation();
    }
}
package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_Map extends Fragment implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LatLng defaultLocation = new LatLng(40.689247,-74.044502);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_map, container, false);

        mMapView = v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this); //this is important

        return v;
    }

    public void placeMarker(String title, double lat, double lon) {
        if (mGoogleMap != null) {
            LatLng marker = new LatLng(lat, lon);
            mGoogleMap.clear();
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15));
            mGoogleMap.addMarker(new MarkerOptions().title(title).position(marker));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(defaultLocation));
        CameraPosition liberty = CameraPosition.builder().target(defaultLocation).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
package com.example.war.fragment;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.war.R;
import com.example.war.logic.data.entity.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Stack;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    public static final String KEY_CAMERA_POSITION = "camera_position";
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private final Stack<MarkerOptions> markers;

    public Fragment_Map() {
        this.markers = new Stack<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        this.mMapView = v.findViewById(R.id.mapView);
        this.mMapView.onCreate(savedInstanceState);
        this.mMapView.getMapAsync(this); //this is important
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MarkerOptions marker = new MarkerOptions();
        while (!this.markers.isEmpty()) {
            marker = this.markers.pop();
            this.mGoogleMap.addMarker(marker);
        }
        if (marker.getPosition() != null) {
            CameraPosition liberty = CameraPosition.builder().target(marker.getPosition()).zoom(16).bearing(0).tilt(45).build();
            this.mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
        }
    }

    public void updateMapFocus(Location location) {
        this.updateMapFocus(CameraPosition.builder()
                .target(new LatLng(location.getLat(), location.getLng()))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build());
    }

    public void updateMapFocus(CameraPosition cameraPosition) {
        if (this.mGoogleMap != null) {
            this.mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    public void placeMarker(String title, String snippet, double lat, double lng) {
        this.markers.push(new MarkerOptions().title(title).snippet(snippet).position(new LatLng(lat, lng)));
    }
}
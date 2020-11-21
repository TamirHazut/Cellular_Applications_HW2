package com.example.war.logic;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.fragment.Fragment_Map;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.TopTenHandler;
import com.example.war.logic.view.OnClickInterface;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

public class TopTenHandlerImplementation implements TopTenHandler {
    private final Fragment_Map mapViewFragment;
    private List<Player> players;
    private Location location;

    public TopTenHandlerImplementation(AppCompatActivity context, RecyclerView recyclerView, List<Player> players) {
        this.mapViewFragment = new Fragment_Map();
        this.players = players;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(players, context, new OnClickInterface() {
            @Override
            public void setOnClick(Location l) {
                location = new Location(l);
                mapViewFragment.updateMapFocus(location);
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        initMap(context);

    }

    private void initMap(AppCompatActivity context) {
        addMarkers();
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.top_ten_FML_map, this.mapViewFragment);
        transaction.commit();
    }

    @Override
    public void updateMapFocus(CameraPosition cameraPosition) {
        this.mapViewFragment.updateMapFocus(cameraPosition);
    }

    @Override
    public void updatePlayersList(List<Player> players) {
        this.players = players;
        addMarkers();
    }

    @Override
    public List<Player> findAllPlayers() {
        return this.players;
    }

    @Override
    public CameraPosition findMapPosition() {
        GoogleMap map = this.mapViewFragment.getGoogleMap();
        if (map != null) {
            return map.getCameraPosition();
        }
        return null;
    }

    public void addMarkers() {
        if (!this.players.isEmpty()) {
            for (Player p : this.players) {
                this.mapViewFragment.placeMarker(String.valueOf(p.getScore()), p.getName(), p.getLocation().getLat(), p.getLocation().getLng());
            }
        }
    }


}

package com.example.war.logic.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.data.Location;
import com.example.war.logic.data.game.Player;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

public class TopTenHandler {
    private final Fragment_Map mapViewFragment;
    private List<Player> players;
    private Location location;

    public TopTenHandler(AppCompatActivity context, RecyclerView recyclerView, List<Player> players) {
        this.mapViewFragment = new Fragment_Map();
        this.players = players;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(players, context, new OnClickInterface() {
            @Override
            public void setOnClick(Location l) {
                location = new Location(l);
                mapViewFragment.setFocus(location);
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


    public GoogleMap getMapView() {
        return this.mapViewFragment.getGoogleMap();
    }

    public void setFocus(CameraPosition cameraPosition) {
        this.mapViewFragment.setFocus(cameraPosition);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        addMarkers();
    }

    public void addMarkers() {
        if (!this.players.isEmpty()) {
            for (Player p : this.players) {
                this.mapViewFragment.placeMarker(String.valueOf(p.getScore()), p.getName(), p.getLocation().getLat(), p.getLocation().getLng());
            }
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}

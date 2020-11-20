package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.war.logic.data.game.Player;
import com.example.war.logic.view.Fragment_Map;
import com.example.war.logic.view.TopTenHandler;
import com.google.android.gms.maps.GoogleMap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Activity_Top_Ten extends AppCompatActivity {
    public static final String LIST = "LIST";
    private TopTenHandler topTenHandler;
    private RecyclerView top_ten_RCV_players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        List<Player> players = (List<Player>) getIntent().getSerializableExtra(LIST);
        findViews();
        this.topTenHandler = new TopTenHandler(this, top_ten_RCV_players, players);
        if (savedInstanceState != null) {
            this.topTenHandler.setPlayers((List<Player>)savedInstanceState.getSerializable(LIST));
            this.topTenHandler.setFocus(savedInstanceState.getParcelable(Fragment_Map.KEY_CAMERA_POSITION));
        }
    }
    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        GoogleMap map = topTenHandler.getMapView();
        if (map != null) {
            outState.putParcelable(Fragment_Map.KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putSerializable(LIST, (ArrayList<Player>)this.topTenHandler.getPlayers());
        }
        super.onSaveInstanceState(outState);
    }

    private void findViews() {
        this.top_ten_RCV_players = findViewById(R.id.top_ten_RCV_players);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Activity_Top_Ten.this, Activity_Main.class);
        startActivity(myIntent);
        finish();
    }
}
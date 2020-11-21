package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.TopTenHandler;
import com.example.war.fragment.Fragment_Map;
import com.example.war.logic.TopTenHandlerImplementation;
import com.google.android.gms.maps.model.CameraPosition;

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
        this.topTenHandler = new TopTenHandlerImplementation(this, top_ten_RCV_players, players);
        if (savedInstanceState != null) {
            this.topTenHandler.updatePlayersList((List<Player>)savedInstanceState.getSerializable(LIST));
            this.topTenHandler.updateMapFocus(savedInstanceState.getParcelable(Fragment_Map.KEY_CAMERA_POSITION));
        }
    }
    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        CameraPosition cameraPosition = topTenHandler.findMapPosition();
        if (cameraPosition != null) {
            outState.putParcelable(Fragment_Map.KEY_CAMERA_POSITION, cameraPosition);
        }
        outState.putSerializable(LIST, (ArrayList<Player>)this.topTenHandler.findAllPlayers());
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
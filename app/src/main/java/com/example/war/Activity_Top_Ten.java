package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.war.logic.data.game.Player;
import com.example.war.logic.view.RecyclerViewHandler;

import java.util.List;

public class Activity_Top_Ten extends AppCompatActivity {
    public static final String LIST = "LIST";
    private RecyclerViewHandler recyclerViewHandler;
    private Button top_ten_BTN_close;
    private Button top_ten_BTN_map;
    private RecyclerView top_ten_RCV_players;
    private Activity_Map mapViewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        List<Player> players = (List<Player>) getIntent().getSerializableExtra(LIST);
        initActivity(players);
        mapViewFragment  = new Activity_Map();
        for (Player p : players) {
            mapViewFragment.placeMarker(p.getName(), p.getLocation().getLat(), p.getLocation().getLng());
        }
    }

    private void initActivity(List<Player> players) {
        findViews();
        initViews();
        this.recyclerViewHandler = new RecyclerViewHandler(this, this.top_ten_RCV_players, players);

    }

    private void findViews() {
        this.top_ten_RCV_players = findViewById(R.id.top_ten_RCV_players);
        this.top_ten_BTN_close = findViewById(R.id.top_ten_BTN_close);
        this.top_ten_BTN_map = findViewById(R.id.top_ten_BTN_map);
    }

    private void initViews() {
        this.top_ten_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.top_ten_BTN_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.top_ten_LAY_bottom, mapViewFragment);
                transaction.commit();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Activity_Top_Ten.this, Activity_Main.class);
        startActivity(myIntent);
        finish();
    }
}
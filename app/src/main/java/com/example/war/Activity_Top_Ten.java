package com.example.war;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.war.logic.Player;
import com.example.war.logic.PlayerConverter;
import com.example.war.logic.view.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Activity_Top_Ten extends AppCompatActivity {
    private RecyclerView top_ten_RCV_players;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Player> players;
    private FirebaseFirestore db;
    private PlayerConverter pConverter;
    private Button top_ten_BTN_close;
    private Button top_ten_BTN_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        initActivity();
    }

    private void initActivity() {
        db = FirebaseFirestore.getInstance();
        this.pConverter = new PlayerConverter();
        this.players = new ArrayList<>();
        getPlayersFromFB();
    }

    private void findViews() {
        this.top_ten_RCV_players = findViewById(R.id.top_ten_RCV_players);
        this.top_ten_BTN_close = findViewById(R.id.top_ten_BTN_close);
        this.top_ten_BTN_map = findViewById(R.id.top_ten_BTN_map);
    }

    private void initViews() {
        this.recyclerViewAdapter = new RecyclerViewAdapter(players, this);
        this.top_ten_RCV_players.setAdapter(recyclerViewAdapter);
        this.top_ten_RCV_players.setLayoutManager(new LinearLayoutManager(this));
        this.top_ten_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Top_Ten.this, Activity_Main.class);
                startActivity(myIntent);
                finish();
            }
        });
        this.top_ten_BTN_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getPlayersFromFB() {
        db.collection("players")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        players.add(pConverter.mapToPlayerConverter(document.getData()));
                    }
                    players.sort(Comparator.reverseOrder());
                    findViews();
                    initViews();
                } else {
                    Log.w("ptttt", "Error getting documents.", task.getException());
                }
            }
        });
    }
}
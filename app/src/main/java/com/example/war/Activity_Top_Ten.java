package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.war.logic.view.RecyclerViewHandler;

public class Activity_Top_Ten extends AppCompatActivity {
    private RecyclerViewHandler recyclerViewHandler;
    private Button top_ten_BTN_close;
    private Button top_ten_BTN_map;
    private RecyclerView top_ten_RCV_players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        initActivity();
    }

    private void initActivity() {
        findViews();
        initViews();
        this.recyclerViewHandler = new RecyclerViewHandler(this, this.top_ten_RCV_players);

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
}
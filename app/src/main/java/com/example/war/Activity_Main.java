package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.war.logic.data.game.Player;
import com.example.war.logic.data.repo.PlayersFirebaseHandler;

import java.io.Serializable;

public class Activity_Main extends AppCompatActivity {
    private PlayersFirebaseHandler playersFirebaseHandler;
    private Button main_BTN_start;
    private Button main_BTN_top_ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playersFirebaseHandler = PlayersFirebaseHandler.getInstance();
        findViews();
        initViews();
    }

    private void findViews() {
        this.main_BTN_start = findViewById(R.id.main_BTN_start);
        this.main_BTN_top_ten = findViewById(R.id.main_BTN_top_ten);
    }

    private void initViews() {
        this.playersFirebaseHandler.getTopTenPlayersFromFB();
        this.main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Main.this, Activity_Game.class);
                startActivity(myIntent);
                finish();
            }
        });
        this.main_BTN_top_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Main.this, Activity_Top_Ten.class);
                myIntent.putExtra(Activity_Top_Ten.LIST, (Serializable) playersFirebaseHandler.getPlayers());
                startActivity(myIntent);
                finish();
            }
        });
    }
}
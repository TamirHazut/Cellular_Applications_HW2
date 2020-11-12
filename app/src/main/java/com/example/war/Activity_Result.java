package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.emitters.StreamEmitter;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class Activity_Result extends AppCompatActivity {
    public static final String RESULT = "RESULT";
    public static final String WINNER_AVATAR = "WINNER_AVATAR";
    private KonfettiView result_viewKonfetti;
    private TextView result_LBL_match_result;
    private ImageView result_IMG_avatar_winner;
    private Button result_BTN_new_game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();
        int result = getIntent().getIntExtra(RESULT, -1);
        int avatar = getIntent().getIntExtra(WINNER_AVATAR, R.drawable.game_end_draw_avatar);
        initViews(result, avatar);
    }

    private void initViews(int result, int avatar) {
        this.result_LBL_match_result.setText(result > 0 ? "Player " + result + " Won!" : "It's a Tie!");
        this.result_IMG_avatar_winner.setBackgroundResource(avatar);
        if (result > 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels/2;
            this.result_viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN)
                    .setDirection(0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                    .addSizes(new Size(12, 4f), new Size(16, 6f))
                    .setPosition(
                            width,
                            -50f)
                    .streamFor(300, StreamEmitter.INDEFINITE);
        }
        this.result_BTN_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_Result.this, Activity_Main.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void findViews() {
        this.result_viewKonfetti = findViewById(R.id.result_viewKonfetti);
        this.result_LBL_match_result = findViewById(R.id.result_LBL_match_result);
        this.result_IMG_avatar_winner = findViewById(R.id.result_IMG_avatar_winner);
        this.result_BTN_new_game = findViewById(R.id.result_BTN_new_game);
    }
}
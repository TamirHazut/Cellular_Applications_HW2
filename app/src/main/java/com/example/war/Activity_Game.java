package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.game.GameHandler;

import java.util.List;

public class Activity_Game extends AppCompatActivity {
    private GameHandler gameHandler;
    private TextView game_LBL_score_p1;
    private ImageView game_IMG_avatar_p1;
    private ImageView game_IMG_card_p1;
    private TextView game_LBL_score_p2;
    private ImageView game_IMG_avatar_p2;
    private ImageView game_IMG_card_p2;
    private ImageView game_IMG_play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initProgram();
    }

    private void initProgram() {
        gameHandler = new GameHandler();
        findViews();
        initViews();
    }

    private void findViews() {
        game_LBL_score_p1 = findViewById(R.id.game_LBL_score_p1);
        game_IMG_avatar_p1 = findViewById(R.id.game_IMG_avatar_p1);
        game_IMG_card_p1 = findViewById(R.id.game_IMG_card_p1);
        game_LBL_score_p2 = findViewById(R.id.game_LBL_score_p2);
        game_IMG_avatar_p2 = findViewById(R.id.game_IMG_avatar_p2);
        game_IMG_card_p2 = findViewById(R.id.game_IMG_card_p2);
        game_IMG_play = findViewById(R.id.game_IMG_play);
    }

    private void initViews() {
        game_LBL_score_p1.setText(String.valueOf(gameHandler.getPlayer(0).getScore()));
        game_IMG_avatar_p1.setBackgroundResource(
                (gameHandler.getPlayer(0).getGender() == Gender.MALE
                        ? R.drawable.user_avatar_male
                        : R.drawable.user_avatar_female));
        game_LBL_score_p2.setText(String.valueOf(gameHandler.getPlayer(1).getScore()));
        game_IMG_avatar_p2.setBackgroundResource(
                (gameHandler.getPlayer(1).getGender() == Gender.MALE
                        ? R.drawable.user_avatar_male
                        : R.drawable.user_avatar_female));
        game_IMG_play.setBackgroundResource(R.drawable.play_button);
        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCards();
            }
        });
    }

    private void drawCards() {
        List<String> drawnCards = gameHandler.drawCards();
       if (drawnCards.isEmpty()) {
            openResult();
        } else {
            int p1_card_id = this.getResources().getIdentifier(drawnCards.get(0), "drawable", this.getPackageName());
            game_IMG_card_p1.setBackgroundResource(p1_card_id);
            int p2_card_id = this.getResources().getIdentifier(drawnCards.get(1), "drawable", this.getPackageName());
            game_IMG_card_p2.setBackgroundResource(p2_card_id);
            game_LBL_score_p1.setText(String.valueOf(gameHandler.getPlayer(0).getScore()));
            game_LBL_score_p2.setText(String.valueOf(gameHandler.getPlayer(1).getScore()));
        }
    }


    private void openResult() {
        int result;
        int avatar;
        if (gameHandler.getPlayer(0).getScore() > gameHandler.getPlayer(1).getScore()) {
            result = 1;
            avatar = (gameHandler.getPlayer(0).getGender() == Gender.MALE
                    ? R.drawable.user_avatar_male
                    : R.drawable.user_avatar_female);
        } else if (gameHandler.getPlayer(1).getScore() > gameHandler.getPlayer(0).getScore()) {
            result = 2;
            avatar = (gameHandler.getPlayer(1).getGender() == Gender.MALE
                    ? R.drawable.user_avatar_male
                    : R.drawable.user_avatar_female);
        } else {
            result = 0;
            avatar = R.drawable.game_end_draw_avatar;
        }
        Intent myIntent = new Intent(Activity_Game.this, Activity_Result.class);
        myIntent.putExtra(Activity_Result.RESULT, result);
        myIntent.putExtra(Activity_Result.WINNER_AVATAR, avatar);
        startActivity(myIntent);
        finish();
    }



}
package com.example.war;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.war.fragment.Fragment_Winner_Dialog;
import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.handler.GameHandler;
import com.example.war.logic.GameHandlerImplementation;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.view.GameCallback;

import java.util.List;

public class Activity_Game extends AppCompatActivity implements GameCallback<String> {
    private GameHandler gameHandler;
    private Player winner;
    private int winnerAvatar;
    private Fragment_Winner_Dialog winnerDialog;
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
        Location playerLocation = (Location) getIntent().getSerializableExtra(Activity_Main.LOCATION);
        initActivity(playerLocation);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Activity_Game.this, Activity_Main.class);
        startActivity(myIntent);
        finish();
    }

    private void initActivity(Location playerLocation) {
        this.gameHandler = new GameHandlerImplementation(playerLocation);
        findViews();
        initViews();
    }

    private void findViews() {
        this.game_LBL_score_p1 = findViewById(R.id.game_LBL_score_p1);
        this.game_IMG_avatar_p1 = findViewById(R.id.game_IMG_avatar_p1);
        this.game_IMG_card_p1 = findViewById(R.id.game_IMG_card_p1);
        this.game_LBL_score_p2 = findViewById(R.id.game_LBL_score_p2);
        this.game_IMG_avatar_p2 = findViewById(R.id.game_IMG_avatar_p2);
        this.game_IMG_card_p2 = findViewById(R.id.game_IMG_card_p2);
        this.game_IMG_play = findViewById(R.id.game_IMG_play);
    }

    private void initViews() {
        Player p1 = this.gameHandler.findPlayerByID(1);
        Player p2 = this.gameHandler.findPlayerByID(2);
        if (p1 != null) {
            this.game_LBL_score_p1.setText(String.valueOf(p1.getScore()));
            this.game_IMG_avatar_p1.setBackgroundResource(
                    (p1.getGender() == Gender.MALE
                            ? R.drawable.user_avatar_male
                            : R.drawable.user_avatar_female));
        }
        if (p2 != null) {
            this.game_LBL_score_p2.setText(String.valueOf(p2.getScore()));
            this.game_IMG_avatar_p2.setBackgroundResource(
                    (p2.getGender() == Gender.MALE
                            ? R.drawable.user_avatar_male
                            : R.drawable.user_avatar_female));
        }

        this.game_IMG_play.setBackgroundResource(R.drawable.play_button);
        this.game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCards();
            }
        });
    }

    private void drawCards() {
        List<String> drawnCards = this.gameHandler.drawCards();
       if (drawnCards.isEmpty()) {
            openResult();
        } else {
            int p1_card_id = this.getResources().getIdentifier(drawnCards.get(0), "drawable", this.getPackageName());
            this.game_IMG_card_p1.setBackgroundResource(p1_card_id);
            int p2_card_id = this.getResources().getIdentifier(drawnCards.get(1), "drawable", this.getPackageName());
            this.game_IMG_card_p2.setBackgroundResource(p2_card_id);
            this.game_LBL_score_p1.setText(String.valueOf(this.gameHandler.findPlayerByID(1).getScore()));
            this.game_LBL_score_p2.setText(String.valueOf(this.gameHandler.findPlayerByID(2).getScore()));
        }
    }

    private void showWinnerNameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        winnerDialog = Fragment_Winner_Dialog.newInstance("Enter name");
        winnerDialog.setGameCallBack(this);
        winnerDialog.show(fm, "fragment_winner_dialog");
    }

    private void openResult() {
        winner = this.gameHandler.findWinner();
        winnerAvatar = (winner == null ?
                R.drawable.game_end_draw_avatar :
                (winner.getGender() == Gender.MALE
                    ? R.drawable.user_avatar_male
                    : R.drawable.user_avatar_female));
        if (winner != null) {
            showWinnerNameDialog();
        } else {
            openResaultActivity();
        }
    }

   private void openResaultActivity() {
        Intent myIntent = new Intent(Activity_Game.this, Activity_Result.class);
        myIntent.putExtra(Activity_Result.WINNER, this.winner);
        myIntent.putExtra(Activity_Result.WINNER_AVATAR, this.winnerAvatar);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onCall(int key, String body) {
        this.winner.setName(body);
        openResaultActivity();
    }
}
package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.war.logic.Card;
import com.example.war.logic.Deck;
import com.example.war.logic.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_Game extends AppCompatActivity {
    private final int NUM_OF_PLAYERS = 2;
    private List<Card> cards;
    private List<Player> players;
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
        loadPlayers();
        initProgram();
    }

    private void initProgram() {
        loadCards();
        createDecks();
        findViews();
        initViews();
    }

    private void initViews() {
        game_LBL_score_p1.setText("" + players.get(0).getScore());
        game_IMG_avatar_p1.setBackgroundResource(
                (players.get(0).getGender() == Player.Gender.MALE
                        ? R.drawable.user_avatar_male
                        : R.drawable.user_avatar_female));
        game_LBL_score_p2.setText("" + players.get(1).getScore());
        game_IMG_avatar_p2.setBackgroundResource(
                (players.get(1).getGender() == Player.Gender.MALE
                        ? R.drawable.user_avatar_male
                        : R.drawable.user_avatar_female));
        game_IMG_play.setBackgroundResource(R.drawable.play_button);
        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!players.get(0).getDeck().isEmpty() || !players.get(0).getDeck().isEmpty()) {
                    drawCards();
                }
            }
        });
    }

    private void drawCards() {
        Card p1_card = players.get(0).getDeck().getCard();
        String p1_card_name = "poker_card_" + p1_card.getSuit() + "_" + p1_card.getValue();
        int p1_card_id = this.getResources().getIdentifier(p1_card_name, "drawable", this.getPackageName());
        game_IMG_card_p1.setBackgroundResource(p1_card_id);
        Card p2_card = players.get(1).getDeck().getCard();
        String p2_card_name = "poker_card_" + p2_card.getSuit() + "_" + p2_card.getValue();
        int p2_card_id = this.getResources().getIdentifier(p2_card_name, "drawable", this.getPackageName());
        game_IMG_card_p2.setBackgroundResource(p2_card_id);
        if (p1_card.getValue() > p2_card.getValue()) {
            players.get(0).addScore();
            game_LBL_score_p1.setText("" + players.get(0).getScore());
        } else if (p2_card.getValue() > p1_card.getValue()) {
            players.get(1).addScore();
            game_LBL_score_p2.setText("" + players.get(1).getScore());
        }
        if (players.get(0).getDeck().isEmpty() || players.get(0).getDeck().isEmpty()) {
            openResult();
        }
    }

    private void openResult() {
        int result;
        int avatar;
        if (players.get(0).getScore() > players.get(1).getScore()) {
            result = 1;
            avatar = (players.get(0).getGender() == Player.Gender.MALE
                    ? R.drawable.user_avatar_male
                    : R.drawable.user_avatar_female);
        } else if (players.get(1).getScore() > players.get(0).getScore()) {
            result = 2;
            avatar = (players.get(1).getGender() == Player.Gender.MALE
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

    private void findViews() {
        game_LBL_score_p1 = findViewById(R.id.game_LBL_score_p1);
        game_IMG_avatar_p1 = findViewById(R.id.game_IMG_avatar_p1);
        game_IMG_card_p1 = findViewById(R.id.game_IMG_card_p1);
        game_LBL_score_p2 = findViewById(R.id.game_LBL_score_p2);
        game_IMG_avatar_p2 = findViewById(R.id.game_IMG_avatar_p2);
        game_IMG_card_p2 = findViewById(R.id.game_IMG_card_p2);
        game_IMG_play = findViewById(R.id.game_IMG_play);
    }

    private void loadCards() {
        cards = new ArrayList<Card>();
        for (Deck.CardSuit cardSuit : Deck.CardSuit.values()) {
            for (Deck.CardValue cardValue : Deck.CardValue.values()) {
                cards.add(new Card(cardSuit, cardValue));
            }
        }
    }

    private void loadPlayers() {
        players = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PLAYERS; ++i) {
            players.add(new Player(i%2 == 0 ? Player.Gender.MALE : Player.Gender.FEMALE));
        }
    }

    private void createDecks() {
        Collections.shuffle(cards);
        List<Card> playerCards = new ArrayList<>();
        int deckSize = cards.size()/NUM_OF_PLAYERS;
        for (int i = 0; i < NUM_OF_PLAYERS; ++i) {
            playerCards.addAll(cards.subList(i*deckSize, (i+1)*deckSize));
            players.get(i).addCardsToDeck(playerCards);
            playerCards.clear();
            players.get(i).printDeck(i);
        }
    }

}
package com.example.war.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.example.war.R;
import com.example.war.logic.Constants;
import com.example.war.logic.GameHandlerImplementation;
import com.example.war.logic.PlayerHandler;
import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.GameHandler;
import com.example.war.logic.view.GameCallback;
import com.google.gson.reflect.TypeToken;
//import com.serhatsurguvec.continuablecirclecountdownview.ContinuableCircleCountDownView;

import java.util.List;

import douglasspgyn.com.github.circularcountdown.CircularCountdown;
import douglasspgyn.com.github.circularcountdown.listener.CircularListener;

public class Fragment_Game extends Fragment_Base_With_Sound {
    private GameHandler gameHandler;
    private Player winner;
    private int winnerAvatar;
    private int p1_card_id;
    private int p2_card_id;
    private static final int MAX_ROUND = 26;
    private int roundNumber;
    private TextRoundCornerProgressBar game_PGB_game_round;
    private TextView game_LBL_score_p1;
    private ImageView game_IMG_avatar_p1;
    private ImageView game_IMG_card_p1;
    private TextView game_LBL_score_p2;
    private ImageView game_IMG_avatar_p2;
    private ImageView game_IMG_card_p2;
    private CircularCountdown game_CCC_play;
    private int countdown_past_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        this.gameHandler = new GameHandlerImplementation(fromJson(getFromSharedPreferences(Constants.LOCATION, ""), Location.class));
        if (savedInstanceState != null) {
            p1_card_id = savedInstanceState.getInt(Constants.P1CARD);
            p2_card_id = savedInstanceState.getInt(Constants.P2CARD);
            roundNumber = savedInstanceState.getInt(Constants.ROUND_NUMBER);
            countdown_past_time = savedInstanceState.getInt(Constants.COUNTDOWN_PAST_TIME);
            this.gameHandler.restorePlayers(fromJson(getFromSharedPreferences(Constants.PLAYERS, ""), new TypeToken<List<PlayerHandler>>() {}.getType()));
        } else {
            this.roundNumber = 0;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        if (savedInstanceState != null) {
            this.game_IMG_card_p1.setBackgroundResource(p1_card_id);
            this.game_IMG_card_p2.setBackgroundResource(p2_card_id);
            configureCountDownProgressBar(countdown_past_time);
        } else {
            playSound(R.raw.game_start, false);
            configureCountDownProgressBar(Constants.COUNTDOWN_DEFAULT_START_TIME);
        }
        initViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.game_CCC_play.isRunning()) {
            this.game_CCC_play.stop();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.gameHandler != null) {
            saveToSharedPreferences(Constants.PLAYERS,
                    toJson(this.gameHandler.sendPlayers(), new TypeToken<List<PlayerHandler>>() {}.getType()));
            outState.putInt(Constants.P1CARD, this.p1_card_id);
            outState.putInt(Constants.P2CARD, this.p2_card_id);
            outState.putInt(Constants.ROUND_NUMBER, this.roundNumber);
            outState.putInt(Constants.COUNTDOWN_PAST_TIME, this.countdown_past_time);
            this.game_CCC_play.stop();
        }
    }

//    @Override
//    public void onCall(int key, String body) {
//        if (this.winner != null) {
//            this.winner.setName(body);
//            openResaultFragment();
//        }
//    }

    private void findViews(View view) {
        this.game_PGB_game_round = view.findViewById(R.id.game_PGB_game_round);
        this.game_LBL_score_p1 = view.findViewById(R.id.game_LBL_score_p1);
        this.game_IMG_avatar_p1 = view.findViewById(R.id.game_IMG_avatar_p1);
        this.game_IMG_card_p1 = view.findViewById(R.id.game_IMG_card_p1);
        this.game_LBL_score_p2 = view.findViewById(R.id.game_LBL_score_p2);
        this.game_IMG_avatar_p2 = view.findViewById(R.id.game_IMG_avatar_p2);
        this.game_IMG_card_p2 = view.findViewById(R.id.game_IMG_card_p2);
        this.game_CCC_play = view.findViewById(R.id.game_CCC_play);
    }

    private void initViews() {
        updateProgressBar();
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
    }

    private void configureCountDownProgressBar(int past_time) {
        this.game_CCC_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game_CCC_play.isRunning()) {
                    game_CCC_play.stop();
                    startCountdownProgressBar(Constants.COUNTDOWN_DEFAULT_START_TIME);
                }
                drawCards();
            }
        });
        startCountdownProgressBar(past_time);
    }

    private void startCountdownProgressBar(int past_time) {
        this.game_CCC_play.create(past_time, Constants.COUNTDOWN_IN_SECONDS, CircularCountdown.TYPE_SECOND).listener(new CircularListener() {
            @Override
            public void onTick(int i) {
                ++countdown_past_time;
            }

            @Override
            public void onFinish(boolean b, int i) {
                countdown_past_time = Constants.COUNTDOWN_DEFAULT_START_TIME;
                drawCards();
            }
        }).start();
    }

    private void updateProgressBar() {
        game_PGB_game_round.setProgressText(this.roundNumber + "/" + MAX_ROUND);
        game_PGB_game_round.setProgress(this.roundNumber);
        int changeColorNumber = MAX_ROUND/3+1;
        if (getActivity() != null) {
            if (roundNumber <= changeColorNumber) {
                game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.red));
            } else if (roundNumber <= changeColorNumber * 2) {
                game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.yellow));
            } else {
                game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.green));
            }
        }
    }

    private void drawCards() {
        this.roundNumber++;
        List<String> drawnCards = this.gameHandler.drawCards();
//        if (getSoundRawId() != R.raw.draw_card) {
//            setSoundRawId(R.raw.draw_card);
//        }
        if (drawnCards.isEmpty()) {
            game_CCC_play.stop();
            openResult();
        } else {
            playSound(R.raw.draw_card, false);
            p1_card_id = this.getResources().getIdentifier(drawnCards.get(0), "drawable", getActivity().getPackageName());
            this.game_IMG_card_p1.setBackgroundResource(p1_card_id);
            p2_card_id = this.getResources().getIdentifier(drawnCards.get(1), "drawable", getActivity().getPackageName());
            this.game_IMG_card_p2.setBackgroundResource(p2_card_id);
            this.game_LBL_score_p1.setText(String.valueOf(this.gameHandler.findPlayerByID(1).getScore()));
            this.game_LBL_score_p2.setText(String.valueOf(this.gameHandler.findPlayerByID(2).getScore()));
        }
        updateProgressBar();
    }

    private void showWinnerNameDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment_Winner_Dialog winnerDialog = Fragment_Winner_Dialog.newInstance("Enter name");
        winnerDialog.setGameCallBack(new GameCallback<String>() {
            @Override
            public void onCall(int key, String body) {
                if (winner != null) {
                    winner.setName(body);
                    openResaultFragment();
                }
            }
        });
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
            openResaultFragment();
        }
    }

    private void openResaultFragment() {
        Fragment_Result fragment_result = new Fragment_Result();
        Bundle args = new Bundle();
        saveToSharedPreferences(Constants.WINNER, toJson(winner, Player.class));
        args.putInt(Constants.WINNER_AVATAR, winnerAvatar);
        fragment_result.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FGMT_container, fragment_result, fragment_result.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}

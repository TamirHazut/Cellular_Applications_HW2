package com.example.war.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.example.war.R;
import com.example.war.logic.GameHandlerImplementation;
import com.example.war.logic.data.DataPassString;
import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.GameHandler;
import com.example.war.logic.view.GameCallback;
import com.example.war.Activity_Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Fragment_Game extends Fragment implements GameCallback<String> {
    private GameHandler gameHandler;
    private Player winner;
    private int winnerAvatar;
    private int p1_card_id;
    private int p2_card_id;
    private final int MAX_ROUND = 26;
    private int roundNumber;
    private Fragment_Winner_Dialog winnerDialog;
    private TextRoundCornerProgressBar game_PGB_game_round;
    private TextView game_LBL_score_p1;
    private ImageView game_IMG_avatar_p1;
    private ImageView game_IMG_card_p1;
    private TextView game_LBL_score_p2;
    private ImageView game_IMG_avatar_p2;
    private ImageView game_IMG_card_p2;
    private ImageView game_IMG_play;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        this.gameHandler = new GameHandlerImplementation(((Activity_Main)getActivity()).getPlayerLocation());
        if (savedInstanceState != null) {
            List<Player> players = (ArrayList<Player>)savedInstanceState.getSerializable(DataPassString.LIST.toString());
            p1_card_id = savedInstanceState.getInt(DataPassString.P1CARD.toString());
            p2_card_id = savedInstanceState.getInt(DataPassString.P2CARD.toString());
            roundNumber = savedInstanceState.getInt(DataPassString.ROUND_NUMBER.toString());
            this.gameHandler.restorePlayers(players);
        } else {
            this.roundNumber = 0;
        }
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                fm.popBackStackImmediate(Fragment_Main.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initViews();
        if (savedInstanceState != null) {
            this.game_IMG_card_p1.setBackgroundResource(p1_card_id);
            this.game_IMG_card_p2.setBackgroundResource(p2_card_id);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.gameHandler != null) {
            outState.putSerializable(DataPassString.LIST.toString(), this.gameHandler.savePlayers());
            outState.putInt(DataPassString.P1CARD.toString(), this.p1_card_id);
            outState.putInt(DataPassString.P2CARD.toString(), this.p2_card_id);
            outState.putInt(DataPassString.ROUND_NUMBER.toString(), this.roundNumber);
        }
    }

    @Override
    public void onCall(int key, String body) {
        if (this.winner != null) {
            this.winner.setName(body);
            openResaultActivity();
        }
    }

    private void findViews(View view) {
        this.game_PGB_game_round = view.findViewById(R.id.game_PGB_game_round);
        this.game_LBL_score_p1 = view.findViewById(R.id.game_LBL_score_p1);
        this.game_IMG_avatar_p1 = view.findViewById(R.id.game_IMG_avatar_p1);
        this.game_IMG_card_p1 = view.findViewById(R.id.game_IMG_card_p1);
        this.game_LBL_score_p2 = view.findViewById(R.id.game_LBL_score_p2);
        this.game_IMG_avatar_p2 = view.findViewById(R.id.game_IMG_avatar_p2);
        this.game_IMG_card_p2 = view.findViewById(R.id.game_IMG_card_p2);
        this.game_IMG_play = view.findViewById(R.id.game_IMG_play);
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

        this.game_IMG_play.setBackgroundResource(R.drawable.play_button);
        this.game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCards();
            }
        });

    }

    private void updateProgressBar() {
        game_PGB_game_round.setProgressText(this.roundNumber + "/" + MAX_ROUND);
        game_PGB_game_round.setProgress(this.roundNumber);
        int changeColorNumber = MAX_ROUND/3+1;
        if (roundNumber <= changeColorNumber) {
            game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.red));
        } else if (roundNumber <= changeColorNumber*2) {
            game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.yellow));
        } else {
            game_PGB_game_round.setProgressColor(ContextCompat.getColor(getActivity(), R.color.green));
        }
    }

    private void drawCards() {
        this.roundNumber++;
        List<String> drawnCards = this.gameHandler.drawCards();
        updateProgressBar();
        if (drawnCards.isEmpty()) {
            openResult();
        } else {
            p1_card_id = this.getResources().getIdentifier(drawnCards.get(0), "drawable", getActivity().getPackageName());
            this.game_IMG_card_p1.setBackgroundResource(p1_card_id);
            p2_card_id = this.getResources().getIdentifier(drawnCards.get(1), "drawable", getActivity().getPackageName());
            this.game_IMG_card_p2.setBackgroundResource(p2_card_id);
            this.game_LBL_score_p1.setText(String.valueOf(this.gameHandler.findPlayerByID(1).getScore()));
            this.game_LBL_score_p2.setText(String.valueOf(this.gameHandler.findPlayerByID(2).getScore()));
        }
    }

    private void showWinnerNameDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
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
        Fragment_Result fragment_result = new Fragment_Result();
        Bundle args = new Bundle();
        args.putSerializable(DataPassString.WINNER.toString(), winner);
        args.putInt(DataPassString.WINNER_AVATAR.toString(), winnerAvatar);
        fragment_result.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FGMT_container, fragment_result, fragment_result.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}

package com.example.war.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.war.Activity_Base;
import com.example.war.R;
import com.example.war.logic.Constants;
import com.example.war.logic.ResultHandlerImplementation;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.ResultHandler;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.emitters.StreamEmitter;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class Fragment_Result extends Fragment_Base {
    private Player winner;
    private int winnerAvatar;
    private ResultHandler resultHandler;
    private KonfettiView result_viewKonfetti;
    private TextView result_LBL_match_result;
    private ImageView result_IMG_avatar_winner;
    private Button result_BTN_new_game;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        winner = fromJson(getFromSharedPreferences(Constants.WINNER, ""), Player.class);
        winnerAvatar =  getArguments().getInt(Constants.WINNER_AVATAR);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            playSound(winnerAvatar == R.drawable.game_end_draw_avatar ? R.raw.game_end_draw : R.raw.game_end_winner);
        }
        resultHandler = new ResultHandlerImplementation(winner);
        findViews(view);
        initViews();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.WINNER_AVATAR, winnerAvatar);
    }

    private void findViews(View view) {
        this.result_viewKonfetti = view.findViewById(R.id.result_viewKonfetti);
        this.result_LBL_match_result = view.findViewById(R.id.result_LBL_match_result);
        this.result_IMG_avatar_winner = view.findViewById(R.id.result_IMG_avatar_winner);
        this.result_BTN_new_game = view.findViewById(R.id.result_BTN_back_to_main_screen);
    }

    private void initViews() {
        this.result_LBL_match_result.setText(this.resultHandler.gameResultMessage());
        this.result_IMG_avatar_winner.setBackgroundResource(winnerAvatar);
        if (this.resultHandler.findWinner() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
            this.resultHandler.addNewScore();
        }
        this.result_BTN_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Base)getActivity()).backToMain();
            }
        });
    }


}

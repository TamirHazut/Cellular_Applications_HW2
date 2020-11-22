package com.example.war.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.war.R;
import com.example.war.logic.ResultHandlerImplementation;
import com.example.war.logic.data.DataPassString;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.ResultHandler;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.emitters.StreamEmitter;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class Fragment_Result extends Fragment {
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
        for (int i = 0; i < getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Log.d("counter", "onCreateView: " + i + " " + getActivity().getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
        winner = (Player) getArguments().getSerializable(DataPassString.WINNER.toString());
        winnerAvatar =  getArguments().getInt(DataPassString.WINNER_AVATAR.toString());
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backToMain();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultHandler = new ResultHandlerImplementation(winner);
        findViews(view);
        initViews();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataPassString.WINNER.toString(), winner);
        outState.putInt(DataPassString.WINNER_AVATAR.toString(), winnerAvatar);
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
                backToMain();
            }
        });
    }

    private void backToMain() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fm.popBackStackImmediate(Fragment_Main.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}

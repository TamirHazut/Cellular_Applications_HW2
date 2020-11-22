package com.example.war.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.war.R;
import com.example.war.logic.view.GameCallback;

public class Fragment_Winner_Dialog extends DialogFragment {
    private String winnerName;
    private GameCallback<String> gameCallback;
    private EditText winner_EDT_winner_name;
    private Button winner_BTN_submit;

    public static Fragment_Winner_Dialog newInstance(String title) {
        Fragment_Winner_Dialog frag = new Fragment_Winner_Dialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_winner_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.winner_EDT_winner_name = view.findViewById(R.id.winner_EDT_winner_name);
        this.winner_BTN_submit = view.findViewById(R.id.winner_BTN_submit);
       initViews();
    }

    public void initViews() {
        String title = getArguments().getString("winner_name_dialog_title", "Enter name");
        getDialog().setTitle(title);
        this.winner_EDT_winner_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        this.setCancelable(false);
        this.winner_BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winnerName = winner_EDT_winner_name.getText().toString();
                if (winnerName != null && !winnerName.isEmpty()) {
                    dismiss();
                }
            }
        });
    }

    public void setGameCallBack(GameCallback gameCallback){
        this.gameCallback = gameCallback;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        int defaultKey = 0;
        if (this.gameCallback != null) {
            gameCallback.onCall(defaultKey, this.winnerName);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}


package com.example.war.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.Constants;
import com.example.war.logic.TopTenHandlerImplementation;
import com.example.war.logic.data.entity.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

public class Fragment_TopTen extends Fragment_Base {
    private RecyclerView top_ten_RCV_players;
    private List<Player> players;
    private RotateLoading top_ten_RotateLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_ten, container, false);
        this.top_ten_RotateLoading = view.findViewById(R.id.top_ten_loader);
        this.top_ten_RotateLoading.start();
        this.players = fromJson(getFromSharedPreferences(Constants.TOP_TEN_PLAYERS, ""),
                new TypeToken<List<Player>>(){}.getType());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            playSound(R.raw.top_ten);
        }
        new Fragment_Map();
        this.top_ten_RCV_players = view.findViewById(R.id.top_ten_RCV_players);
        try {
            new TopTenHandlerImplementation((AppCompatActivity) getActivity(), this.top_ten_RCV_players, players);
        } catch (ClassCastException ex) {
            Log.e("Fragment_TopTen onViewVreated", ex.getMessage());
        }
        top_ten_RotateLoading.stop();
    }
}

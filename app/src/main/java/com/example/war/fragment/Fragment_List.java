package com.example.war.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.Constants;
import com.example.war.logic.TopTenHandlerImplementation;
import com.example.war.logic.data.entity.Player;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Fragment_List extends Fragment_Base {
     private RecyclerView top_ten_RCV_players;
     private List<Player> players;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.players = fromJson(getFromSharedPreferences(Constants.TOP_TEN_PLAYERS, ""),
                new TypeToken<List<Player>>(){}.getType());
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        this.top_ten_RCV_players = view.findViewById(R.id.top_ten_RCV_players);
    }

    private void initViews() {
        try {
            new TopTenHandlerImplementation((AppCompatActivity) getActivity(), this.top_ten_RCV_players, players);
        } catch (ClassCastException ex) {
            Log.e("Fragment_TopTen onViewVreated", ex.getMessage());
        }
    }


}


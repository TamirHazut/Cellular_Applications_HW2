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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.TopTenHandlerImplementation;
import com.example.war.logic.data.DataPassString;
import com.example.war.logic.data.entity.Player;

import java.util.ArrayList;

public class Fragment_TopTen extends Fragment {
    private RecyclerView top_ten_RCV_players;
    private ArrayList<Player> players;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_ten, container, false);
        players = (ArrayList<Player>) getArguments().getSerializable(DataPassString.LIST.toString());
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
        new Fragment_Map();
        this.top_ten_RCV_players = view.findViewById(R.id.top_ten_RCV_players);
        try {
            new TopTenHandlerImplementation((AppCompatActivity) getActivity(), this.top_ten_RCV_players, players);
        } catch (ClassCastException ex) {
            Log.e("Fragment_TopTen onViewVreated", ex.getMessage());
        }
    }
}

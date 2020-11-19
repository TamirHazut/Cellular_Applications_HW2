package com.example.war.logic.view;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.game.Player;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RecyclerViewHandler {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    public RecyclerViewHandler(Context context, RecyclerView recyclerView, List<Player> players) {
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = new RecyclerViewAdapter(players, context);
        this.recyclerView.setAdapter(recyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void notifyDataSetChanged() {
        this.recyclerViewAdapter.notifyDataSetChanged();
    }

}

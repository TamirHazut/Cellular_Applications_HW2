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
    private List<Player> players;
    private FirebaseFirestore db;
    private PlayerConverter pConverter;

    public RecyclerViewHandler(Context context, RecyclerView recyclerView) {
        this.players = new ArrayList<>();
        this.recyclerView = recyclerView;
        this.db = FirebaseFirestore.getInstance();
        this.pConverter = new PlayerConverter();
        this.recyclerViewAdapter = new RecyclerViewAdapter(players, context);
        this.recyclerView.setAdapter(recyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getPlayersFromFB();
    }

    public void getPlayersFromFB() {
        AtomicInteger count = new AtomicInteger();
        db.collection("players").get().addOnSuccessListener(
                listRes -> listRes.forEach(
                        document -> {
                            players.add(pConverter.mapToPlayerConverter(document.getData()));
                            count.getAndIncrement();
                            if (count.get() == listRes.size()) {
                                players.sort(Comparator.reverseOrder());
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                ));
    }
}

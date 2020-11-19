package com.example.war.logic.data.repo;


import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.game.Player;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayersFirebaseHandler {
    private static PlayersFirebaseHandler instance = null;
    private final String COLLECTION_PATH = "players";
    private final int MAX_LIST_SIZE = 10;
    private ArrayList<Player> players;
    private FirebaseFirestore db;
    private PlayerConverter pConverter;

    private PlayersFirebaseHandler() {
        this.players = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
        this.pConverter = new PlayerConverter();
    }

    public void getTopTenPlayersFromFB() {
        if (!this.players.isEmpty()) {
            this.players.clear();
        }
        AtomicInteger count = new AtomicInteger();
        db.collection(COLLECTION_PATH).get().addOnSuccessListener(
                listRes -> listRes.forEach(
                        document -> {
                            players.add(pConverter.mapToPlayerConverter(document.getData()));
                            count.getAndIncrement();
                            if (count.get() == listRes.size()) {
                                players.sort(Comparator.reverseOrder());
                            }
                        }
                ));
    }

    public void addNewScore(Player player) {
        DocumentReference ref = db.collection(COLLECTION_PATH).document();
        ref.set(pConverter.playerToMap(player));
    }

    public ArrayList<Player> getPlayers() {
        return (this.players.size() < MAX_LIST_SIZE ? this.players : new ArrayList<>(this.players.subList(0, MAX_LIST_SIZE)));
    }

    public static PlayersFirebaseHandler getInstance() {
        if (instance == null) {
            instance = new PlayersFirebaseHandler();
        }
        return instance;
    }
}

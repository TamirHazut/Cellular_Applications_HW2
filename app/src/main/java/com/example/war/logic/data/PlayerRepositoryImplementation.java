package com.example.war.logic.data;

import com.example.war.logic.PlayerConverterImplementation;
import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.data.repo.PlayerRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerRepositoryImplementation implements PlayerRepository {
    private final String COLLECTION_PATH = "scores";
    private final int MAX_LIST_SIZE = 10;
    private final ArrayList<Player> players;
    private final FirebaseFirestore db;
    private final PlayerConverter playerConverter;

    public PlayerRepositoryImplementation() {
        this.players = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
        this.playerConverter = new PlayerConverterImplementation();
    }

    @Override
    public void updateTopPlayers() {
        if (!this.players.isEmpty()) {
            this.players.clear();
        }
        db.collection(COLLECTION_PATH)
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(
                        listRes -> listRes.forEach(
                            document -> {
                                players.add(playerConverter.mapToPlayer(document.getData()));
                            }
                        )
                );
    }

    @Override
    public void addNewScore(Player player) {
        DocumentReference ref = db.collection(COLLECTION_PATH).document();
        ref.set(playerConverter.playerToMap(player));
    }

    @Override
    public ArrayList<Player> findTopPlayers() {
        return (this.players.size() < MAX_LIST_SIZE ? this.players : new ArrayList<>(this.players.subList(0, MAX_LIST_SIZE)));
    }
}

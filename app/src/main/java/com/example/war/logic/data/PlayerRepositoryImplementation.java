package com.example.war.logic.data;

import android.content.SharedPreferences;

import com.example.war.logic.Constants;
import com.example.war.logic.PlayerConverterImplementation;
import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.data.repo.PlayerRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryImplementation implements PlayerRepository {
    private final String COLLECTION_PATH = "scores";
    private final int MAX_LIST_SIZE = 10;
    private final List<Player> players;
    private final FirebaseFirestore db;
    private final PlayerConverter playerConverter;
    private SharedPreferences prefs;
    private Gson gson;

    public PlayerRepositoryImplementation() {
        this.players = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
        this.playerConverter = new PlayerConverterImplementation();
    }

    public PlayerRepositoryImplementation(SharedPreferences prefs) {
        this.prefs = prefs;
        this.gson = new Gson();
        this.players = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
        this.playerConverter = new PlayerConverterImplementation();
    }

    public PlayerRepositoryImplementation setGson(Gson gson) {
        this.gson = gson;
        return this;
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
                        listRes -> {
                            listRes.forEach(
                                    document -> {
                                        players.add(playerConverter.mapToPlayer(document.getData()));
                                    }
                            );
                            if (players != null) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString(Constants.TOP_TEN_PLAYERS,
                                        gson.toJson(players, new TypeToken<List<Player>>() {
                                        }.getType())).apply();
                            }
                        }
                );
    }

    @Override
    public void addNewScore(Player player) {
        DocumentReference ref = db.collection(COLLECTION_PATH).document();
        ref.set(playerConverter.playerToMap(player));
    }

    @Override
    public List<Player> findTopPlayers() {
        return (this.players.size() < MAX_LIST_SIZE ? this.players : new ArrayList<>(this.players.subList(0, MAX_LIST_SIZE)));
    }
}

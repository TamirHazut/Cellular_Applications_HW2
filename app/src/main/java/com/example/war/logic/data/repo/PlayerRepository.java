package com.example.war.logic.data.repo;

import com.example.war.logic.data.entity.Player;

import java.util.ArrayList;

public interface PlayerRepository {

    public void updateTopPlayers();

    public ArrayList<Player> findTopPlayers();

    public void addNewScore(Player player);

}

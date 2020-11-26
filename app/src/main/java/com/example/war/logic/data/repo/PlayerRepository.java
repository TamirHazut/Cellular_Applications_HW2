package com.example.war.logic.data.repo;

import com.example.war.logic.data.entity.Player;

import java.util.List;

public interface PlayerRepository {

    public void updateTopPlayers();

    public List<Player> findTopPlayers();

    public void addNewScore(Player player);

}

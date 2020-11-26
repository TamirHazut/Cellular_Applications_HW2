package com.example.war.logic.handler;

import com.example.war.logic.PlayerHandler;
import com.example.war.logic.data.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface GameHandler {
    public List<String> drawCards();

    public Player findPlayerByID(int id);

    public Player findWinner();

    public void restorePlayers(List<PlayerHandler> players);

    public ArrayList<PlayerHandler> sendPlayers();
}

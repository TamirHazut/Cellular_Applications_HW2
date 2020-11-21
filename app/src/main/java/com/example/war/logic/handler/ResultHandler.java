package com.example.war.logic.handler;

import com.example.war.logic.data.entity.Player;

public interface ResultHandler {
    public String gameResultMessage();

    public Player findWinner();

    public void addNewScore();
}

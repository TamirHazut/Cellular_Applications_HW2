package com.example.war.logic;

import com.example.war.logic.data.entity.Card;
import com.example.war.logic.data.entity.Player;


public class PlayerHandler {
    private Player player;

    public PlayerHandler() {
    }

    public PlayerHandler(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerHandler setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Card drawCard() {
        return this.player.drawCard();
    }

    public boolean hasCards() {
        return this.player.hasCards();
    }

    public void addScore() {
        this.player.addScore();
    }
}

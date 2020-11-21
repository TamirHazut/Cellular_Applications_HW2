package com.example.war.logic;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Card;
import com.example.war.logic.data.entity.Deck;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.GameHandler;

import java.util.ArrayList;
import java.util.List;

public class GameHandlerImplementation implements GameHandler {
    private final int NUM_OF_PLAYERS = 2;
    private List<Player> players;

    public GameHandlerImplementation(Location playerLocation) {
        loadPlayers(playerLocation);
        distributeDecks();
    }

    private void distributeDecks() {
        List<Deck> decks = Deck.createDecks(NUM_OF_PLAYERS);
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players.get(i).setDeck(decks.get(i));
        }
    }

    private void loadPlayers(Location playerLocation) {
        players = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PLAYERS; ++i) {
            players.add(new Player(i%2 == 0 ? Gender.MALE : Gender.FEMALE, playerLocation));
        }
    }

    @Override
    public List<String> drawCards() {
        List<String> cardsDrawn = new ArrayList<>();
        Card p1_card = getPlayer(0).drawCard();
        String p1_card_name = "poker_card_" + p1_card.getSuit() + "_" + p1_card.getValue();
        Card p2_card = getPlayer(1).drawCard();
        String p2_card_name = "poker_card_" + p2_card.getSuit() + "_" + p2_card.getValue();
        if (p1_card.getValue() > p2_card.getValue()) {
            getPlayer(0).addScore();
        } else if (p2_card.getValue() > p1_card.getValue()) {
            getPlayer(1).addScore();
        }
        if (getPlayer(0).getDeck().isEmpty() || getPlayer(1).getDeck().isEmpty()) {
            return cardsDrawn;
        }
        cardsDrawn.add(p1_card_name);
        cardsDrawn.add(p2_card_name);
        return cardsDrawn;
    }

    @Override
    public Player findPlayerByID(int index) {
        if (this.players == null || index < 1 || index > this.players.size()) {
            return null;
        }
        return this.players.get(index-1);
    }

    @Override
    public Player findWinner() {
        Player winner = null;
        int draw = 0;
        for (Player player : this.players) {
            if (winner == null) {
                winner = player;
                ++draw;
            } else {
                int res = winner.compareTo(player);
                if (res < 0) {
                    winner = player;
                } else if (res == 0) {
                    ++draw;
                }
            }
        }
        if (draw == this.players.size()) {
            winner = null;
        }
        return winner;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }
}

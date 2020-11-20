package com.example.war.logic.data.game;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.Location;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    private final int NUM_OF_PLAYERS = 2;
    private List<Player> players;

    public GameHandler(Location playerLocation) {
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

    public Player getPlayer(int index) {
        return players.get(index);
    }
}

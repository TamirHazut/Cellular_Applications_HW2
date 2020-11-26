package com.example.war.logic;

import com.example.war.logic.data.CardSuit;
import com.example.war.logic.data.CardValue;
import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Card;
import com.example.war.logic.data.entity.Deck;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.handler.GameHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameHandlerImplementation implements GameHandler {
    private final int NUM_OF_PLAYERS = 2;
    private List<PlayerHandler> playersHandler;

    public GameHandlerImplementation() {
    }

    public GameHandlerImplementation(Location playerLocation) {
        loadPlayers(playerLocation);
        distributeDecks();
    }

    @Override
    public List<String> drawCards() {
        List<String> cardsDrawn = new ArrayList<>();
        Card p1_card = drawCard(0);
        String p1_card_name = "poker_card_" + p1_card.getSuit() + "_" + p1_card.getValue();
        Card p2_card = drawCard(1);
        String p2_card_name = "poker_card_" + p2_card.getSuit() + "_" + p2_card.getValue();
        if (p1_card.getValue() > p2_card.getValue()) {
            addScore(0);
        } else if (p2_card.getValue() > p1_card.getValue()) {
            addScore(1);
        }
        if (hasCards(0) || hasCards(1)) {
            return cardsDrawn;
        }
        cardsDrawn.add(p1_card_name);
        cardsDrawn.add(p2_card_name);
        return cardsDrawn;
    }

    @Override
    public Player findPlayerByID(int index) {
        if (this.playersHandler == null || index < 1 || index > this.playersHandler.size()) {
            return null;
        }
        return this.playersHandler.get(index-1).getPlayer();
    }

    @Override
    public Player findWinner() {
        Player winner = null;
        int draw = 0;
        for (PlayerHandler playerHandler : this.playersHandler) {
            if (winner == null) {
                winner = playerHandler.getPlayer();
                ++draw;
            } else {
                int res = winner.compareTo(playerHandler.getPlayer());
                if (res < 0) {
                    winner = playerHandler.getPlayer();
                } else if (res == 0) {
                    ++draw;
                }
            }
        }
        if (draw == this.playersHandler.size()) {
            winner = null;
        }
        return winner;
    }

    @Override
    public void restorePlayers(List<PlayerHandler> playersHandler) {
        if (playersHandler != null && playersHandler.size() > 0) {
            this.playersHandler = playersHandler;
        }
    }

    @Override
    public ArrayList<PlayerHandler> sendPlayers() {
        return (ArrayList<PlayerHandler>) this.playersHandler;
    }

    private void distributeDecks() {
        List<Deck> decks = GameHandlerImplementation.createDecks(NUM_OF_PLAYERS);
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            playersHandler.get(i).getPlayer().setDeck(decks.get(i));
        }
    }

    private void loadPlayers(Location playerLocation) {
        this.playersHandler = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PLAYERS; ++i) {
            this.playersHandler.add(new PlayerHandler().setPlayer(new Player().setGender(i%2 == 0 ? Gender.MALE : Gender.FEMALE).setLocation(playerLocation)));
        }
    }

    private boolean hasCards(int i) {
        return this.playersHandler.get(i).hasCards();
    }

    private void addScore(int i) {
        this.playersHandler.get(i).addScore();
    }

    private Card drawCard(int i) {
        return this.playersHandler.get(i).drawCard();
    }


    public static List<Card> loadCards() {
        List<Card> allCards  = new ArrayList<>();
        for (CardSuit cardSuit : CardSuit.values()) {
            for (CardValue cardValue : CardValue.values()) {
                allCards.add(new Card(cardSuit, cardValue));
            }
        }
        return allCards;
    }

    public static List<Deck> createDecks(int numOfDecks) {
        List<Card> allCards = loadCards();
        Collections.shuffle(allCards);
        List<Deck> decks = new ArrayList<>();
        int deckSize = allCards.size()/numOfDecks;
        for (int i = 0; i < numOfDecks; ++i) {
            decks.add(new Deck());
            decks.get(i).addCards(allCards.subList(i*deckSize, (i+1)*deckSize));
        }
        return decks;
    }
}

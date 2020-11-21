package com.example.war.logic.data.entity;

import com.example.war.logic.data.CardSuit;
import com.example.war.logic.data.CardValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck implements Serializable {
    private final Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
    }

    public Stack<Card> getCards() {
        return this.cards;
    }

    public void addCards(List<Card> card) {
        this.cards.addAll(card);
    }

    public Card getCard() {
        return this.getCards().pop();
    }

    public boolean isEmpty() {
        return this.getCards().isEmpty();
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

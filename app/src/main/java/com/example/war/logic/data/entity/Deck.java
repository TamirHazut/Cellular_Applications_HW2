package com.example.war.logic.data.entity;

import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
    }

    public Card getCard() {
        if (this.cards != null && !this.cards.isEmpty()) {
            return this.cards.pop();
        }
        return null;
    }

    public void addCards(List<Card> card) {
        this.cards.addAll(card);
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

}

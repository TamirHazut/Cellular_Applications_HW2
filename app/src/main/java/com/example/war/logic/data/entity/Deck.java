package com.example.war.logic.data.entity;

import com.example.war.logic.data.CardSuit;
import com.example.war.logic.data.CardValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
    }

    public Deck(Stack<Card> cards) {
        this.cards = cards;
    }

    public Stack<Card> getCards() {
        return this.cards;
    }

    public Deck setCards(Stack<Card> cards) {
        this.cards = cards;
        return this;
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

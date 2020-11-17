package com.example.war.logic.data.game;

import android.util.Log;

import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
    }

    public Stack<Card> getCards() {
        return this.cards;
    }

    public void addCard(List<Card> card) {
        this.cards.addAll(card);
    }

    public Card getCard() {
        return this.getCards().pop();
    }

    public boolean isEmpty() {
        return this.getCards().isEmpty();
    }

    public void printCards(int i) {
        for (Card card : this.getCards()) {
            Log.d("ptttt" + i, card.toString());
        }
    }
}

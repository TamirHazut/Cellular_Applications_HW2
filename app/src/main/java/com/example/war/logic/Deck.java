package com.example.war.logic;

import android.util.Log;

import java.util.List;
import java.util.Stack;

public class Deck {


    public static enum CardSuit { CLUB, DIAMOND, HEART, SPADE };
    public static enum CardValue {
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(11), QUEEN(12), KING(13);
        private final int cardValue;
        CardValue(int cardValue) {
            this.cardValue = cardValue;
        }
        public int value() {
            return this.cardValue;
        }
    };

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

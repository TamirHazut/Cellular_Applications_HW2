package com.example.war.logic.data.game;

import java.io.Serializable;

public class Card implements Serializable {
    private CardSuit cardSuit;
    private CardValue cardValue;

    public Card(CardSuit cardSuit, CardValue cardValue) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
    }

    public int getValue() {
        return cardValue.value();
    }

    public String getSuit() { return this.cardSuit.toString().toLowerCase(); }

    @Override
    public String toString() {
        return "Card{" +
                "cardSuit=" + cardSuit +
                ", cardValue=" + cardValue +
                '}';
    }
}

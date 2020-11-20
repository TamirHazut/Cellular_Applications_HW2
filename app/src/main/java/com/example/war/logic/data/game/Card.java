package com.example.war.logic.data.game;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Card implements Serializable {
    private final CardSuit cardSuit;
    private final CardValue cardValue;

    public Card(CardSuit cardSuit, CardValue cardValue) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
    }

    public int getValue() {
        return cardValue.value();
    }

    public String getSuit() { return this.cardSuit.toString().toLowerCase(); }

    @NotNull
    @Override
    public String toString() {
        return "Card{" +
                "cardSuit=" + cardSuit +
                ", cardValue=" + cardValue +
                '}';
    }
}

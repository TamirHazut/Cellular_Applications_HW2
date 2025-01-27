package com.example.war.logic.data.entity;

import com.example.war.logic.data.CardSuit;
import com.example.war.logic.data.CardValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Card {
    private CardSuit cardSuit;
    private CardValue cardValue;

    public Card() {
    }

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

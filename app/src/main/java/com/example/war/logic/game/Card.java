package com.example.war.logic.game;

public class Card {
    private Deck.CardSuit cardSuit;
    private Deck.CardValue cardValue;

    public Card(Deck.CardSuit cardSuit, Deck.CardValue cardValue) {
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

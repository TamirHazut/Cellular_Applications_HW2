package com.example.war.logic;

import java.util.List;

public class Player {
    public static enum Gender {MALE, FEMALE};
    private int score;
    private Gender gender;
    private Deck deck;

    public Player(Gender gender)
    {
        this.deck = new Deck();
        this.gender = gender;
        this.score = 0;
    }

    public Gender getGender() {
        return gender;
    }

    public int getScore() {
        return this.score;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public void addCardsToDeck(List<Card> card) {
        this.getDeck().addCard(card);
    }

    public void addScore() {
        this.score++;
    }

    public void printDeck(int i) {
        this.getDeck().printCards(i);
    }
}

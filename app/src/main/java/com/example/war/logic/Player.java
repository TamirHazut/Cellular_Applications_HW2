package com.example.war.logic;

import com.example.war.logic.game.Card;
import com.example.war.logic.game.Deck;

import java.util.List;
import java.util.Objects;

public class Player implements Comparable<Player> {

    private String name;
    private int score;
    private Gender gender;
    private Deck deck;
    private Location location;

    public Player(String name, Gender gender, int score, Location location) {
        this.name = name;
        this.deck = new Deck();
        this.score = score;
        this.gender = gender;
        this.location = location;
    }

    public Player(Gender gender)
    {
        this("Player", gender, 0, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public Gender getGender() {
        return gender;
    }

    public int getScore() {
        return this.score;
    }

    public Location getLocation() {
        return location;
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

    @Override
    public int compareTo(Player o) {
        return this.getScore()-o.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}

package com.example.war.logic.data.entity;

import com.example.war.logic.data.Gender;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Comparable<Player> {
    private String name;
    private int score;
    private Gender gender;
    private Deck deck;
    private Location location;

    public Player() {
    }

    public Player(String name, Gender gender, int score, Location location) {
        this.name = name;
        this.deck = new Deck();
        this.score = score;
        this.gender = gender;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Gender getGender() {
        return gender;
    }

    public Player setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player setDeck(Deck deck) {
        this.deck = deck;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Player setLocation(Location location) {
        this.location = location;
        return this;
    }

    public void addScore() {
        this.score++;
    }

    public Card drawCard() { return this.deck.getCard(); }

    public boolean hasCards() {
        return this.getDeck().isEmpty();
    }

    @Override
    public int compareTo(Player o) {
        return this.getScore()-o.getScore();
    }

    @NotNull
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", gender=" + gender +
                ", deck=" + deck +
                ", location=" + location +
                '}';
    }

}

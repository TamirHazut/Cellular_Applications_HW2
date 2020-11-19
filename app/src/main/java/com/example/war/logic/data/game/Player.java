package com.example.war.logic.data.game;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.Location;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Player implements Comparable<Player>, Serializable {

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
        this("Tamir", gender, 0, new Location(31.906927, 35.014116));
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

    public Card drawCard() { return this.deck.getCard(); }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void addScore() {
        this.score++;
    }

    @Override
    public int compareTo(Player o) {
        return this.getScore()-o.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

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

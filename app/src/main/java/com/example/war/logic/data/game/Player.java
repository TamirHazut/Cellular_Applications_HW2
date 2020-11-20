package com.example.war.logic.data.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.Location;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Comparable<Player>, Serializable {

    private String name;
    private int score;
    private Gender gender;
    private Deck deck;
    private final Location location;

    public Player(String name, Gender gender, int score, Location location) {
        this.name = name;
        this.deck = new Deck();
        this.score = score;
        this.gender = gender;
        this.location = location;
    }

    public Player(Gender gender, Location playerLocation)
    {
        this("Player", gender, 0, playerLocation);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setGender(Gender gender) { this.gender = gender; }

    public Gender getGender() {
        return gender;
    }

    public void addScore() {
        this.score++;
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

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() { return this.deck.getCard(); }

    @Override
    public int compareTo(Player o) {
        return this.getScore()-o.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
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

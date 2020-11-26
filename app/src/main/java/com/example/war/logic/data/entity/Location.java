package com.example.war.logic.data.entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Location {
    private double lat;
    private double lng;

    public Location() {
    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location(Location location) {
        this(location.getLat(), location.getLng());
    }

    public double getLat() {
        return lat;
    }

    public Location setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Location setLng(double lng) {
        this.lng = lng;
        return this;
    }

    @NotNull
    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

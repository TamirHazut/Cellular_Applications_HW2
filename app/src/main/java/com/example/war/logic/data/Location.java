package com.example.war.logic.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Location implements Serializable {
    private final double lat;
    private final double lng;

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

    public double getLng() {
        return lng;
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

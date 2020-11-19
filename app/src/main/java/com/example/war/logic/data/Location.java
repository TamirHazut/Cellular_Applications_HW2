package com.example.war.logic.data;

import java.io.Serializable;

public class Location implements Serializable {
    private Double lat;
    private Double lng;

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

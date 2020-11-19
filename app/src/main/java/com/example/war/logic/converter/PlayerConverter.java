package com.example.war.logic.converter;

import android.util.Log;

import com.example.war.logic.data.Gender;
import com.example.war.logic.data.Location;
import com.example.war.logic.data.game.Player;
import com.google.firebase.firestore.GeoPoint;

import java.util.Map;
import java.util.TreeMap;

public class PlayerConverter {
    public Player mapToPlayerConverter(Map<String, Object> map) {
        int score = ((Long)map.get("score")).intValue();
        String name = (String)map.get("name");
        Gender gender = Gender.valueOf(((String)map.get("gender")).toUpperCase());
        return new Player(name, gender, score, geoPointToLocation((GeoPoint) map.get("location")));
    }
    public Location geoPointToLocation(GeoPoint coord) {
        return new Location(coord.getLatitude(), coord.getLongitude());
    }

    public Map<String, Object> playerToMap(Player player) {
        Map<String, Object> map = new TreeMap<>();
        map.put("name", player.getName());
        map.put("gender", player.getGender().toString());
        map.put("score", player.getScore());
        map.put("location", locationToGeoPoint(player.getLocation()));
        return map;
    }
    public GeoPoint locationToGeoPoint(Location location) {
        return new GeoPoint(location.getLat(), location.getLng());
    }
}

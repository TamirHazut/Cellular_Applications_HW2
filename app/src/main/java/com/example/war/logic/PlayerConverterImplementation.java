package com.example.war.logic;

import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.Gender;
import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Player;
import com.google.firebase.firestore.GeoPoint;

import java.util.Map;
import java.util.TreeMap;

public class PlayerConverterImplementation implements PlayerConverter {
    public Player mapToPlayer(Map<String, Object> map) {
        int score = ((Long)map.get("score")).intValue();
        String name = (String)map.get("name");
        Gender gender = Gender.valueOf(((String)map.get("gender")).toUpperCase());
        return new Player(name, gender, score, geoPointToLocation((GeoPoint) map.get("location")));
    }
    public Location geoPointToLocation(GeoPoint geoPoint) {
        return new Location(geoPoint.getLatitude(), geoPoint.getLongitude());
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

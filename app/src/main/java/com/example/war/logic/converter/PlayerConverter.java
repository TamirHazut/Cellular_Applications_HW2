package com.example.war.logic.converter;

import com.example.war.logic.data.entity.Location;
import com.example.war.logic.data.entity.Player;
import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

public interface PlayerConverter {
    public Player mapToPlayer(Map<String, Object> map);

    public Map<String, Object> playerToMap(Player player);

    public Location geoPointToLocation(GeoPoint geoPoint);

    public GeoPoint locationToGeoPoint(Location location);
}

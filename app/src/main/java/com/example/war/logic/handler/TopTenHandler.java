package com.example.war.logic.handler;

import com.example.war.logic.data.entity.Player;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

public interface TopTenHandler {
    public void updatePlayersList(List<Player> players);

    public void updateMapFocus(CameraPosition cameraPosition);

    public List<Player> findAllPlayers();

    public CameraPosition findMapPosition();
}

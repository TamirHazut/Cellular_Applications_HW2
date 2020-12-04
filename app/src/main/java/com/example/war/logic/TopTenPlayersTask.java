package com.example.war.logic;

import android.content.SharedPreferences;

import com.example.war.logic.data.PlayerRepositoryImplementation;
import com.example.war.logic.data.repo.PlayerRepository;

public class TopTenPlayersTask implements Runnable {

    private PlayerRepository playersRepository;

    public TopTenPlayersTask(SharedPreferences prefs) {
        this.playersRepository = new PlayerRepositoryImplementation(prefs);
    }

    @Override
    public void run() {
        try {
            this.playersRepository.updateTopPlayers();
        } catch (IllegalThreadStateException ex) {
        }
    }
}

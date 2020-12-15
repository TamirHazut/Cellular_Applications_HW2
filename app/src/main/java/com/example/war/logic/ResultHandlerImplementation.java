package com.example.war.logic;

import com.example.war.logic.data.PlayerRepositoryImplementation;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.data.repo.PlayerRepository;
import com.example.war.logic.handler.ResultHandler;

public class ResultHandlerImplementation implements ResultHandler {
    private PlayerRepository playersRepository;
    private Player winner;

    public ResultHandlerImplementation() {
    }

    public ResultHandlerImplementation(Player winner) {
        this.playersRepository = new PlayerRepositoryImplementation();
        this.winner = winner;
    }

    @Override
    public String gameResultMessage() {
        return this.winner != null ? this.winner.getName() + Constants.WIN_STRING : Constants.TIE_STRING;
    }

    @Override
    public Player findWinner() {
        return this.winner;
    }

    @Override
    public void addNewScore() {
        if (this.winner != null) {
            this.playersRepository.addNewScore(this.winner);
        }
    }
}

package com.example.war.logic;

import com.example.war.logic.data.PlayerRepositoryImplementation;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.data.repo.PlayerRepository;
import com.example.war.logic.handler.ResultHandler;

public class ResultHandlerImplementation implements ResultHandler {
    private final String TIE_STRING = "It's a Tie!";
    private final String WIN_STRING =  " Won!";
    private PlayerRepository playersRepository;
    private Player winner;

    public ResultHandlerImplementation() {
    }

    public ResultHandlerImplementation(Player winner) {
        this.playersRepository = new PlayerRepositoryImplementation();
        this.winner = winner;
    }

    public PlayerRepository getPlayersRepository() {
        return playersRepository;
    }

    public ResultHandlerImplementation setPlayersRepository(PlayerRepository playersRepository) {
        this.playersRepository = playersRepository;
        return this;
    }

    public Player getWinner() {
        return winner;
    }

    public ResultHandlerImplementation setWinner(Player winner) {
        this.winner = winner;
        return this;
    }

    @Override
    public String gameResultMessage() {
        return this.winner != null ? this.winner.getName() + WIN_STRING : TIE_STRING;
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

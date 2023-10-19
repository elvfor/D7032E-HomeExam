package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

/**
 * This class holds the context for a certain game and handles the states of the
 * game. Has
 * abstrasctions of the logic (which holds chosen rules and scoring) and all
 * players for this round
 */
public class GameContext {

    private ArrayList<Player> players;
    private GameLogic gameLogic;
    private IGameState currentState;
    private int currentGameRound;
    private int roundsToRun;
    private int currentRound;

    public GameContext(ArrayList<Player> players, GameLogic gameLogic) {
        this.players = players;
        this.gameLogic = gameLogic;
        currentGameRound = 0;
    }

    /**
     * Always execute the next state as long as there is a state to execute on
     */
    public void startGame() {
        while (currentState != null) {
            try {
                currentState.executeAction(players, gameLogic, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setRoundsToRun(int roundsToRun) {
        this.roundsToRun = roundsToRun;
    }

    public int getRoundsToRun() {
        return roundsToRun;
    }

    public void setCurrentState(IGameState state) {
        this.currentState = state;
    }

    public IGameState getCurrentState() {
        return currentState;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentGameRound() {
        return currentGameRound;
    }

    public void setCurrentGameRound(int gameRound) {
        this.currentGameRound = gameRound;
    }

}

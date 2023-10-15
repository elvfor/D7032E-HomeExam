package game.state;

import java.io.IOException;
import java.util.ArrayList;

import game.logic.GameLogic;
import player.Player;

public class GameContext {

    private ArrayList<Player> players;
    private GameLogic gameLogic;
    private IGameState currentState;
    private int currentGameRound;

    public GameContext(ArrayList<Player> players, GameLogic gameLogic) {
        this.players = players;
        this.gameLogic = gameLogic;
        currentGameRound = 0;
    }

    public void startGame() {
        while (currentState != null) {
            try {
                currentState.executeAction(players, gameLogic, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

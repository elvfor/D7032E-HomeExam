package game.gameContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import card.AustralianCard;
import card.Card;
import game.logic.GameLogic;
import game.logic.IGameRules;
import game.scoring.IScoring;
import game.state.IGameState;
import game.state.InitRoundState;
import player.HumanPlayer;
import player.Player;

public class GameContext {
    
    private ArrayList<Player> players;
    private GameLogic gameLogic;
    private IGameState currentState;
    private Player activePlayer;
    private int currentGameRound;

    public GameContext(ArrayList<Player> players, GameLogic gameLogic){
        this.players = players;
        this.gameLogic = gameLogic;
        currentGameRound = 0;
        setCurrentState(new InitRoundState());

        startGame();
    }

    private void startGame(){
        while(currentState != null){
            try {
                currentState.executeAction(players, gameLogic, this);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void setCurrentState(IGameState state){
        this.currentState = state;
    }

    public IGameState getCurrentState(){
        return currentState;
    } 

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public int getCurrentGameRound(){
        return currentGameRound;
    }

    public void setCurrentGameRound(int gameRound){
        this.currentGameRound = gameRound;
        }

}

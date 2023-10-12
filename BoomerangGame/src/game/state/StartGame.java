package game.state;

import java.io.IOException;
import java.util.ArrayList;

import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.Player;

public class StartGame implements IGameState{

    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        System.out.println("the game has started");
        IGameState GameOverState = new GameOverState();

        game.setCurrentState(GameOverState);
    }
    
}
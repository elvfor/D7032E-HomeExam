package game.state;

import java.util.ArrayList;

import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.Player;

public class GameOverState implements IGameState {

    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        System.out.println("Game is over");
        game.setCurrentState(null);

    }
}

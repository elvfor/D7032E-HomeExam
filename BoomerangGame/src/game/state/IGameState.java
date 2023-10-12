package game.state;

import java.io.IOException;
import java.util.ArrayList;

import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.Player;

public interface IGameState {
    void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException;
}

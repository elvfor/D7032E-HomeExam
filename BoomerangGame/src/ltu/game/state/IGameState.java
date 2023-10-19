package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

/**
 * This interface represents the logic that all game states should implement.
 * Has a function that all states implements and executes differently. Take all
 * players in the game, the logic that holds rules and scoring and the context
 * for handling the states
 */
public interface IGameState {
    void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException;
}

package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

public interface IGameState {
    void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException;
}

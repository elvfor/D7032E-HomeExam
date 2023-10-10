package game.state;

import java.io.IOException;

import game.gameContext.GameContext;

public interface IGameState {
    void executeAction(GameContext context) throws IOException;
}

package game.state;

import java.io.IOException;

public interface IGameState {
    void executeAction(GameContext context) throws IOException;
}

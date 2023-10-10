package game.state;

import java.io.IOException;

public class StartGame implements IGameState{

    @Override
    public void executeAction(GameContext context) throws IOException {
        System.out.println("the game has started");
        IGameState GameOverState = new GameOverState();
        
        context.setCurrentState(GameOverState);
    }
    
}
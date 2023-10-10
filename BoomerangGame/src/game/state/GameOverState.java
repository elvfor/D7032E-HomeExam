package game.state;

public class GameOverState implements IGameState {

    @Override
    public void executeAction(GameContext context) {
        System.out.println("Game is over");
        context.setCurrentState(null);

    }
}

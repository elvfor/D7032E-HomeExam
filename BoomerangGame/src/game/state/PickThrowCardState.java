package game.state;

import java.io.IOException;

import game.gameContext.GameContext;
import player.Player;

public class PickThrowCardState implements IGameState{

    @Override
    public void executeAction(GameContext game) throws IOException {
        for(Player player : game.getPlayers()){
            game.printTest(player);
            //game.printCurrentHand(player);
            player.getPlayerActions().pickThrowCard(player);
            //game.printCurrentHand(player);
        }
        IGameState GameOverState = new GameOverState();
        game.setCurrentState(GameOverState);
    }
    
}

package game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.Player;

public class PickLastCardState implements IGameState{

    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size()); 
            CountDownLatch latch = new CountDownLatch(game.getPlayers().size()); 
    
            for (Player player : game.getPlayers()) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gameLogic.printCurrentDraft(player);
                            gameLogic.printCurrentHand(player);
                            player.getPlayerActions().pickThrowCard(player);
                        } finally {
                            latch.countDown(); // Signal that this task is complete
                        }
                    }
                };
                threadpool.execute(task);
            }
    
            try {
                // Wait until all tasks are complete
                latch.await();
            } catch (InterruptedException e) {
                // Handle InterruptedException, if necessary
            }
            gameLogic.getGameRules().passLastCards(game.getPlayers());
            IGameState roundScoreState = new RoundScoreState();
            game.setCurrentState(roundScoreState);
            
    }
    
}

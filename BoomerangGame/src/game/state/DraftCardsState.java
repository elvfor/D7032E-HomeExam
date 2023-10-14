package game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.logic.GameLogic;
import player.Player;

public class DraftCardsState implements IGameState{
    private int roundsToRun = 6;
    private int currentRound = 0 ;
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        if (currentRound < roundsToRun) {
            ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size()); 
            CountDownLatch latch = new CountDownLatch(game.getPlayers().size()); 
    
            for (Player player : game.getPlayers()) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gameLogic.setCurrentPlayer(player);
                            gameLogic.printCurrentHand();
                            player.getPlayerActions().pickCardFromHand(player);
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
            currentRound++;
            if(currentRound == roundsToRun) {
                gameLogic.getGameRules().passLastCards(game.getPlayers());
                gameLogic.printAllPlayersDraft(game.getPlayers());
                currentRound = 0;
                IGameState nexState = new RoundScoreState();
                game.setCurrentState(nexState);
            }else{
                gameLogic.getGameRules().passCards(game.getPlayers());
                gameLogic.printAllPlayersDraft(game.getPlayers());
            }

        }
    }
}

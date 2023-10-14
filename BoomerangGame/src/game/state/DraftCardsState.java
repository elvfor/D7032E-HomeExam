package game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.logic.GameLogic;
import player.Player;

public class DraftCardsState implements IGameState {
    private int roundsToRun = 7;
    private int currentRound = 1;

    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        if (currentRound < roundsToRun) {
            executeRound(players, gameLogic, game);
            currentRound++;

            if (currentRound == roundsToRun) {
                handleLastRound(players, gameLogic, game);
            } else {
                handleRegularRound(players, gameLogic, game);
            }
        }
    }

    private void executeRound(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size());
        CountDownLatch latch = new CountDownLatch(game.getPlayers().size());

        for (Player player : game.getPlayers()) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        gameLogic.printCurrentDraft(player);
                        gameLogic.printCurrentHand(player);
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
    }

    private void handleLastRound(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        gameLogic.getGameRules().passLastCards(game.getPlayers());
        gameLogic.printAllPlayersDraft(game.getPlayers());
        currentRound = 0;
        IGameState nexState = new RoundScoreState();
        game.setCurrentState(nexState);
    }

    private void handleRegularRound(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        gameLogic.getGameRules().passCards(game.getPlayers());
        gameLogic.printAllPlayersDraft(game.getPlayers());
    }
}

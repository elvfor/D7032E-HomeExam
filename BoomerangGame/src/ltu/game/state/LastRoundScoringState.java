package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

/**
 * This class represents the state that calculates and prints the score after
 * the last game round is played and take the players to the final state/end the
 * game
 * Implements
 * the IGameState.
 */
public class LastRoundScoringState implements IGameState {
    /**
     * @param players   All players in the game
     * @param gameLogic The object that holds game logic such as which scoring and
     *                  rules
     * @param game      The object that controls the state pattern
     * @throws IOException
     */
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        calculateFinalScores(players, gameLogic, game);
        gameLogic.checkWinner(players);
        game.setCurrentState(null);
    }

    private void calculateFinalScores(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size());
        CountDownLatch latch = new CountDownLatch(game.getPlayers().size());

        for (Player player : game.getPlayers()) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        gameLogic.getScoring().totalScore(player, gameLogic);
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
}

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
 * each game round is played and take the players to the next state. Implements
 * the IGameState.
 */
public class RoundScoreState implements IGameState {
    /**
     * @param players   All players in the game
     * @param gameLogic The object that holds game logic such as which scoring and
     *                  rules
     * @param game      The object that controls the state pattern
     * @throws IOException
     */
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        calculateRoundScores(players, gameLogic, game);
        gameLogic.printAfterRound(players);
        checkAndSetNextState(players, gameLogic, game);
    }

    private void calculateRoundScores(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {

        ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size());
        CountDownLatch latch = new CountDownLatch(game.getPlayers().size());

        for (Player player : game.getPlayers()) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        gameLogic.getScoring().roundScore(player, gameLogic);
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

    private void checkAndSetNextState(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        if (game.getCurrentGameRound() == 4) {
            IGameState lastRoundScoringState = new LastRoundScoringState();
            game.setCurrentState(lastRoundScoringState);
        } else {
            IGameState initRoundState = new InitRoundState();
            game.setCurrentState(initRoundState);
        }
    }

}

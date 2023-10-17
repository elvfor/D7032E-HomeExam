package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

public class RoundScoreState implements IGameState {

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

package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

/**
 * This class represents the state that allows players to draft and pass cards
 * during the play-through and take the players to the next state. Implements
 * the IGameState.
 */
public class DraftCardsState implements IGameState {

    /**
     * @param players   All players in the game
     * @param gameLogic The object that holds game logic such as which scoring and
     *                  rules
     * @param game      The object that controls the state pattern
     * @throws IOException
     */
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        if (game.getCurrentRound() < game.getRoundsToRun()) {
            executeRound(players, gameLogic, game);
            int round = game.getCurrentRound();
            game.setCurrentRound(round + 1);

            if (game.getCurrentRound() == game.getRoundsToRun()) {
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
        game.setCurrentRound(1);
        IGameState nexState = new RoundScoreState();
        game.setCurrentState(nexState);
    }

    private void handleRegularRound(ArrayList<Player> players, GameLogic gameLogic, GameContext game) {
        gameLogic.getGameRules().passCards(game.getPlayers());
        gameLogic.printAllPlayersDraft(game.getPlayers());
    }
}

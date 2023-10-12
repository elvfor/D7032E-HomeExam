package game.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import card.AustralianCard;
import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.HumanPlayer;
import player.Player;

public class PickThrowCardState implements IGameState{

    @Override
public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
    ExecutorService threadpool = Executors.newFixedThreadPool(game.getPlayers().size());
    CountDownLatch latch = new CountDownLatch(game.getPlayers().size());

    for (Player player : game.getPlayers()) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
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
        System.out.println("newtest");
        gameLogic.getGameRules().passCards(game.getPlayers());
        IGameState pickCardAndPassState = new PickCardAndPassState();
        game.setCurrentState(pickCardAndPassState);
    }
    
}

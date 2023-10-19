package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;

import ltu.game.logic.GameLogic;
import ltu.card.Card;
import ltu.player.Player;

/**
 * This class represents the state that initializes the game rounds for players
 * such as shuffling and dealing out cards and take the players to the next
 * state. Implements
 * the IGameState.
 */
public class InitRoundState implements IGameState {
    /**
     * @param players   All players in the game
     * @param gameLogic The object that holds game logic such as which scoring and
     *                  rules
     * @param game      The object that controls the state pattern
     * @throws IOException
     */
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        game.setCurrentGameRound(game.getCurrentGameRound() + 1);
        game.setCurrentRound(1);
        game.setRoundsToRun(7);
        gameLogic.clearBeforeRound(players);
        ArrayList<Card> deck = gameLogic.getDeck(players);
        ArrayList<Card> shuffledCards = gameLogic.getGameRules().shuffleCards(deck);
        gameLogic.getGameRules().dealCards(shuffledCards, players);
        IGameState nextState = new DraftCardsState();
        game.setCurrentState(nextState);
    }
}
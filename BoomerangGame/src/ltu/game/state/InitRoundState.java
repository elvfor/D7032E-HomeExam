package ltu.game.state;

import java.io.IOException;
import java.util.ArrayList;

import ltu.game.logic.GameLogic;
import ltu.card.Card;
import ltu.player.Player;

public class InitRoundState implements IGameState {
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
package game.state;

import java.io.IOException;
import java.util.ArrayList;

import card.Card;
import game.logic.GameLogic;
import player.Player;

public class InitRoundState implements IGameState{
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        game.setCurrentGameRound(game.getCurrentGameRound()+1);
        gameLogic.clearBeforeRound(players);
        ArrayList<Card> deck = gameLogic.getDeck(players);
        ArrayList<Card> shuffledCards = gameLogic.getGameRules().shuffleCards(deck);
        gameLogic.getGameRules().dealCards(shuffledCards, players);
        IGameState nexState = new DraftCardsState();
        game.setCurrentState(nexState);
    }
}
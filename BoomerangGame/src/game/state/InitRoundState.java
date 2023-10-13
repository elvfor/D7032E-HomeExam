package game.state;

import java.io.IOException;
import java.util.ArrayList;

import card.Card;
import game.gameContext.GameContext;
import game.logic.GameLogic;
import player.Player;

public class InitRoundState implements IGameState{
    @Override
    public void executeAction(ArrayList<Player> players, GameLogic gameLogic, GameContext game) throws IOException {
        //game.printTest();
        game.setCurrentGameRound(game.getCurrentGameRound()+1);
        ArrayList<Card> deck = gameLogic.resetRound(players);
        ArrayList<Card> shuffledCards = gameLogic.getGameRules().shuffleCards(deck);
        //gameLogic.setCards(shuffledCards);
        gameLogic.getGameRules().dealCards(shuffledCards, game.getPlayers());
        IGameState nexState = new PickThrowCardState();
        game.setCurrentState(nexState);
    }
}

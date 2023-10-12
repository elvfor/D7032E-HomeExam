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
        ArrayList<Card> shuffledCards = gameLogic.getGameRules().shuffleCards(gameLogic.getCards());
        gameLogic.setCards(shuffledCards);
        gameLogic.getGameRules().dealCards(gameLogic.getCards(), game.getPlayers());
        IGameState nexState = new PickThrowCardState();
        game.setCurrentState(nexState);
    }
}

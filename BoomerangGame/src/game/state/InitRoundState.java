package game.state;

import java.io.IOException;
import java.util.ArrayList;

import card.Card;
import game.gameContext.GameContext;

public class InitRoundState implements IGameState{
    @Override
    public void executeAction(GameContext game) throws IOException {
        ArrayList<Card> shuffledCards = game.getGameLogic().shuffleCards(game.getCards());
        game.setCards(shuffledCards);
        game.getGameLogic().dealCards(game.getCards(), game.getPlayers());
        IGameState nexState = new PickThrowCardState();
        game.setCurrentState(nexState);
    }
}

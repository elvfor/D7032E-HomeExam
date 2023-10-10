package game.state;

import java.util.List;
import card.Card;
import game.rules.IRules;
import game.scoring.IScoring;
import player.Player;

public class GameContext {
    private IRules rules;
    private IScoring scoring;
    private List<Player> players;
    private List<Card> cards;
    private IGameState currentState;
    private Player activePlayer;

    /*public void GameContext(List<Player> players, List<Card> cards, GameState currentState, Player activePlayer){
        this.players = players;
        this.cards = cards;
        this.currentState = currentState;
        this.activePlayer = activePlayer;
    }*/

    public void setCurrentState(IGameState state){
        this.currentState = state;
    }

    public IGameState getCurrentState(){
        return currentState;
    } 

    public void setPlayers(List<Player> players){
        this.players = players;
    }
    public void setCards(List<Card> cards){
        this.cards = cards;
    }

    public void setRules(IRules rules){
        this.rules = rules;
    }

    public void setScoring(IScoring scoring){
        this.scoring = scoring;
    }
}

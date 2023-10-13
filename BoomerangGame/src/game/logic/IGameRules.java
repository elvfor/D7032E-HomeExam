package game.logic;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import player.Player;

public interface IGameRules {
    void passCards(ArrayList<Player> players);
    void passLastCards(ArrayList<Player> players);
    ArrayList<Card> shuffleCards(ArrayList<Card> cards);
    void dealCards(ArrayList<Card> cards, ArrayList<Player> players);
}

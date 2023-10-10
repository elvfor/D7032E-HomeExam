package game.logic;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import player.Player;

public interface IGameLogic {
    void passCards();
    void cardDraft();
    ArrayList<Card> shuffleCards(ArrayList<Card> cards);
    void dealCards(ArrayList<Card> cards, ArrayList<Player> players);
}

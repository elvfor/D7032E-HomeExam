package ltu.game.logic;

import java.util.ArrayList;

import ltu.card.Card;
import ltu.player.Player;

public interface IGameRules {
    void passCards(ArrayList<Player> players);

    void passLastCards(ArrayList<Player> players);

    ArrayList<Card> shuffleCards(ArrayList<Card> cards);

    void dealCards(ArrayList<Card> cards, ArrayList<Player> players);
}

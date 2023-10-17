package ltu.game.logic;

import java.util.ArrayList;
import java.util.Collections;

import ltu.card.Card;
import ltu.player.Player;

public class standardGameRules implements IGameRules {

    @Override
    public void passCards(ArrayList<Player> players) {

        for (Player p : players) {
            int pid = p.getPlayerID();
            Player sendHandTo = (pid < (players.size() - 1)) ? players.get(pid + 1) : players.get(0);

            for (Card c : p.getNextPlayersHand()) {
                sendHandTo.getHand().add(c);
            } // grab the cards passed on from the previous player
            p.getNextPlayersHand().clear();
        }
    }

    @Override
    public ArrayList<Card> shuffleCards(ArrayList<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    @Override
    public void dealCards(ArrayList<Card> deck, ArrayList<Player> players) {
        // Requirement 4
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                if (!deck.isEmpty()) {
                    Card card = deck.remove(0); // Remove the top card from the deck
                    player.getHand().add(card); // Add the card to the player's hand
                } else {
                    System.out.println("Card is empty");

                }
            }
        }
    }

    @Override
    public void passLastCards(ArrayList<Player> players) {
        for (Player p : players) {
            int pid = p.getPlayerID();
            Player sendHandTo = (pid > 0) ? players.get(pid - 1) : players.get(players.size() - 1);

            for (Card c : p.getNextPlayersHand()) {
                sendHandTo.getDraft().add(c);
            } // grab the cards passed on from the previous player
            p.getNextPlayersHand().clear();
        }
    }
}

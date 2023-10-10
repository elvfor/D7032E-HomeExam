package game.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import card.Card;
import player.Player;

public class standardGameLogic implements IGameLogic{

    @Override
    public void passCards() {
        System.out.println("passing cards");
    }

    @Override
    public void cardDraft() {
        System.out.println("Drafting cards");

    }

    @Override
    public ArrayList<Card> shuffleCards(ArrayList<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    @Override
    public void dealCards(ArrayList<Card> cards, ArrayList<Player> players) {
        //Requirement 4
		for(Player player : players) {
			for (int i = 0; i < 7; i++) {
                if (!cards.isEmpty()) {
                    Card card = cards.remove(0); // Remove the top card from the deck
                    player.getHand().add(card);  // Add the card to the player's hand
                } else {
                    System.out.println("Card is empty");

                }
            }
        }
    }
    
}

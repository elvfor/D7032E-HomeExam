package ltu.card;

import java.util.ArrayList;

/**
 * This interface holds the functions that all cards should have in common.
 */
public interface ICardFunctionalities {
    String printCards(ArrayList<Card> cards);

    String printCard(Card card);
}

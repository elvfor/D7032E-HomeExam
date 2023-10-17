package test;

import static org.junit.Assert.*;
import org.junit.Test;

import ltu.card.AustralianCard;
import ltu.card.Card;

import ltu.main.BoomerangGame;

public class BoomerangGameTest {
    private BoomerangGame BoomerangGameTest = null;

    // Requirement 1
    @Test
    public void testCheckNrOfPlayerReqOK() throws Exception {
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(2, 0));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(4, 0));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(2, 2));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(1, 3));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(1, 1));
    }

    @Test
    public void testCheckNrOfPlayerReqNotOK() throws Exception {
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(2, 3));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(5, 0));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(1, 0));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(0, 5));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(3, 3));
    }

    // Requirement 2. The deck consists of 28 cards
    @Test
    public void testCreateCardsLength() {

        String version = "Australia";

        // Call the createCards method
        Card[] cards = BoomerangGame.createCards(version);

        // Assertions for the requirements:

        assertEquals(28, cards.length);
    }

    // Requirement 2a. The name of the tourist site and the corresponding letter
    // (A-Z)
    @Test
    public void testCreateCardsLetters() {
        String version = "Australia";

        // Call the createCards method
        Card[] cards = BoomerangGame.createCards(version);
        for (char siteLetter = 'A'; siteLetter <= 'Z'; siteLetter++) {
            boolean found = false;
            for (Card card : cards) {
                if (card.getLetter().charAt(0) == siteLetter) {
                    found = true;
                    break;
                }
            }
            assertTrue("Tourist site name with letter " + siteLetter + " not found", found);
        }
    }

    // Requirement 2b. A number (used for calculating the Throw and catch score)
    @Test
    public void testCreateCardsNumber() {
        String version = "Australia";

        Card[] cards = BoomerangGame.createCards(version);

        for (Card card : cards) {
            assertTrue("Card number should be greater than or equal to 1", card.getNumber() >= 1);
        }
    }

    // Requirement 2c. Two of the following additional symbols:
    @Test
    public void testCreateCardsSymbols() {
        String version = "Australia";

        Card[] cards = BoomerangGame.createCards(version);

        for (Card card : cards) {
            assertTrue("Card should contain additional symbols", hasAdditionalSymbols(card));
        }
    }

    private boolean hasAdditionalSymbols(Card card) {
        if (card instanceof AustralianCard) {
            AustralianCard acard = (AustralianCard) card;
            return acard.getCollection() != null || acard.getAnimal() != null || acard.getActivity() != null;

        } else {
            return false;
        }
    }

}
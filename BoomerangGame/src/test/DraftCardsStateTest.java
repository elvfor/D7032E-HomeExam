package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import ltu.card.AustralianCard;
import ltu.card.Card;
import ltu.game.logic.GameLogic;
import ltu.game.logic.GameLogicFactory;
import ltu.game.logic.IGameRules;
import ltu.game.scoring.IScoring;
import ltu.game.state.DraftCardsState;
import ltu.game.state.GameContext;
import ltu.main.BoomerangGame;
import ltu.player.Player;
import ltu.player.actions.BotPlayerActionsStandard;
import ltu.player.actions.IPlayerActions;

public class DraftCardsStateTest {

    DraftCardsState draftCardsState;
    GameLogic mockGameLogic;
    GameContext gameContext;
    Card[] cards;
    ArrayList<Player> players;
    Player player1;
    Player player2;
    Player player3;

    @Before
    public void setUp() {
        // Initialize the InitRoundState with a mock GameLogic
        IGameRules gameRules = GameLogicFactory.createGameRules("Standard");
        IScoring scoring = GameLogicFactory.createScoring("Australia");
        String version = "Australia";
        cards = BoomerangGame.createCards(version);
        String[] regions = BoomerangGame.createRegions(version);
        mockGameLogic = new GameLogic(gameRules, scoring, cards, regions);
        draftCardsState = new DraftCardsState();
        IPlayerActions playerActions = new BotPlayerActionsStandard();
        players = new ArrayList<>();

        // Create a GameContext with players
        gameContext = new GameContext(players, null);
        player1 = new Player(0, playerActions);
        player2 = new Player(1, playerActions);
        player3 = new Player(2, playerActions);
        players.add(player1);
        players.add(player2);

        gameContext = new GameContext(players, mockGameLogic);
        gameContext.setCurrentState(draftCardsState);
        cards[0] = new AustralianCard("The Bungle Bungles", "A", "Western Australia", 1, "Leaves", "",
                "Indigenous Culture");
        cards[1] = new AustralianCard("The Pinnacles", "B", "Western Australia", 1, "", "Kangaroos", "Sightseeing");
        cards[2] = new AustralianCard("Margaret River", "C", "Western Australia", 1, "Shells", "Kangaroos", "");
        cards[3] = new AustralianCard("Kalbarri National Park", "D", "Western Australia", 1, "Wildflowers", "",
                "Bushwalking");
        cards[4] = new AustralianCard("Uluru", "E", "Northern Territory", 4, "", "Emus", "Indigenous Culture");
        cards[5] = new AustralianCard("Kakadu National Park", "F", "Northern Territory", 4, "", "Wombats",
                "Sightseeing");
        cards[6] = new AustralianCard("Nitmiluk National Park", "G", "Northern Territory", 4, "Shells", "Platypuses",
                "");
        player1.getHand().add(cards[0]);
        player1.getHand().add(cards[1]);
        player1.getHand().add(cards[2]);
        player1.getHand().add(cards[3]);
        player1.getHand().add(cards[4]);
        player1.getHand().add(cards[5]);
        player1.getHand().add(cards[6]);
        cards[7] = new AustralianCard("King's Canyon", "H", "Northern Territory", 4, "", "Koalas", "Swimming");
        cards[8] = new AustralianCard("The Great Barrier Reef", "I", "Queensland", 6, "Wildflowers", "", "Sightseeing");
        cards[9] = new AustralianCard("The Whitsundays", "J", "Queensland", 6, "", "Kangaroos", "Indigenous Culture");
        cards[10] = new AustralianCard("Daintree Rainforest", "K", "Queensland", 6, "Souvenirs", "", "Bushwalking");
        cards[11] = new AustralianCard("Surfers Paradise", "L", "Queensland", 6, "Wildflowers", "", "Swimming");
        cards[12] = new AustralianCard("Barossa Valley", "M", "South Australia", 3, "", "Koalas", "Bushwalking");
        cards[13] = new AustralianCard("Lake Eyre", "N", "South Australia", 3, "", "Emus", "Swimming");
        player2.getHand().add(cards[7]);
        player2.getHand().add(cards[8]);
        player2.getHand().add(cards[9]);
        player2.getHand().add(cards[10]);
        player2.getHand().add(cards[11]);
        player2.getHand().add(cards[12]);
        player2.getHand().add(cards[13]);
        cards[14] = new AustralianCard("Kangaroo Island", "O", "South Australia", 3, "", "Kangaroos", "Bushwalking");
        cards[15] = new AustralianCard("Mount Gambier", "P", "South Australia", 3, "Wildflowers", "", "Sightseeing");
        cards[16] = new AustralianCard("Blue Mountains", "Q", "New South Whales", 5, "", "Wombats",
                "Indigenous Culture");
        cards[17] = new AustralianCard("Sydney Harbour", "R", "New South Whales", 5, "", "Emus", "Sightseeing");
        cards[18] = new AustralianCard("Bondi Beach", "S", "New South Whales", 5, "", "Wombats", "Swimming");
        cards[19] = new AustralianCard("Hunter Valley", "T", "New South Whales", 5, "", "Emus", "Bushwalking");
        cards[20] = new AustralianCard("Melbourne", "U", "Victoria", 2, "", "Wombats", "Bushwalking");

    }

    // Requirement 5. Select a Throw Card
    @Test
    public void testSelectedThrowCard() throws IOException {
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        for (Player p : players) {
            assertEquals(1, p.getDraft().size());
        }
    }

    // Requirement 5. Check that the Throw card is not revealed to other players
    @Test
    public void testHiddenThrowCard() throws IOException {
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        StringBuilder message = new StringBuilder();
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        int pID = 1;
        message.append("Player ").append(pID).append(" has drafted a hidden Throw Card. \n");
        assertEquals(message.toString(), mockGameLogic.generateDraftMessage(players, 0));
    }

    // Requirement 6. Pass remaining cards to next players hand
    @Test
    public void testPassCards() throws IOException {
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);

        ArrayList<Card> hand1before = new ArrayList<>(player1.getHand());
        ArrayList<Card> hand2before = new ArrayList<>(player2.getHand());

        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);

        // Check player1's hand and draft, this will check that the last player sent to
        // the first
        for (Card card : player1.getHand()) {
            String cardLetter = card.getLetter();

            // Check if the card's letter is in hand2before
            boolean foundInHand2 = false;
            for (Card otherCard : hand2before) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInHand2 = true;
                    break; // No need to continue checking if the letter is found
                }
            }
            boolean foundInPlayer2Draft = false;
            for (Card otherCard : player2.getDraft()) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInPlayer2Draft = true;
                    break; // No need to continue checking if the letter is found
                }
            }

            assertTrue("Card " + cardLetter + " not found in hand2before or player2's draft",
                    foundInHand2 || foundInPlayer2Draft);

        }
        // Check player2's hand and draft, this will check that player 1 sent to player
        // 2
        for (Card card : player2.getHand()) {
            String cardLetter = card.getLetter();

            // Check if the card's letter is in hand2before
            boolean foundInHand1 = false;
            for (Card otherCard : hand1before) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInHand1 = true;
                    break; // No need to continue checking if the letter is found
                }
            }
            boolean foundInPlayer1Draft = false;
            for (Card otherCard : player1.getDraft()) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInPlayer1Draft = true;
                    break; // No need to continue checking if the letter is found
                }
            }

            assertTrue("Card " + cardLetter + " not found in hand1before or player1's draft",
                    foundInHand1 || foundInPlayer1Draft);

        }

    }

    // Requirement 7. Each player selects one card from their new hand and shows it
    // to the other players
    @Test
    public void testSelectAndShow1Card() throws IOException {
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        mockGameLogic.getGameRules().passCards(players);
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        int currentPlayerID = 0;
        StringBuilder message = new StringBuilder();

        message.append("Player ").append(currentPlayerID + 1).append(" has drafted\n")
                .append(mockGameLogic
                        .printCards(new ArrayList<>(player2.getDraft().subList(1, player2.getDraft().size()))))
                .append("\n");
        assertEquals(message.toString(), mockGameLogic.generateDraftMessage(players, 0));
    }

    // Requirement 8. Continue step 6 and 7 until only one card remain on hand. Show
    // all cards selected in these steps to the other players.
    @Test
    public void testSelectAndShowCards() throws IOException {

        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        mockGameLogic.getGameRules().passCards(players);

        for (int i = 0; i < 6; i++) {
            gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        }

        int currentPlayerID = 0;
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("Player ").append(currentPlayerID + 1).append(" has drafted\n")
                .append(mockGameLogic
                        .printCards(new ArrayList<>(player2.getDraft().subList(1, player2.getDraft().size()))))
                .append("\n");

        assertEquals(expectedMessage.toString(), mockGameLogic.generateDraftMessage(players, currentPlayerID));
    }

    // Requirement 9. The final card is passed to the previous player in the
    // sequence (the first player passes the card to the last player)
    @Test
    public void testPassLastCard() throws IOException {
        players.add(player3);
        player3.getHand().add(cards[14]);
        player3.getHand().add(cards[15]);
        player3.getHand().add(cards[16]);
        player3.getHand().add(cards[17]);
        player3.getHand().add(cards[18]);
        player3.getHand().add(cards[1]);
        player3.getHand().add(cards[20]);
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        mockGameLogic.getGameRules().passCards(players);

        for (int i = 0; i < 6; i++) {
            gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        }
        ArrayList<Card> hand1before = new ArrayList<>(player1.getHand());
        ArrayList<Card> hand2before = new ArrayList<>(player2.getHand());
        ArrayList<Card> hand3before = new ArrayList<>(player3.getHand());
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);

        // Check player3's hand and draft and see if it contains a card from Player 1's
        // last hand
        for (Card card : player1.getHand()) {
            String cardLetter = card.getLetter();

            // Check if the card's letter is in hand2before
            boolean foundInHand3 = false;
            for (Card otherCard : hand3before) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInHand3 = true;
                    break; // No need to continue checking if the letter is found
                }
            }
            boolean foundInPlayer3Draft = false;
            for (Card otherCard : player3.getDraft()) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInPlayer3Draft = true;
                    break; // No need to continue checking if the letter is found
                }
            }

            assertTrue("Card " + cardLetter + " not found in hand3before or player3's draft",
                    foundInHand3 || foundInPlayer3Draft);

        }
        // Check player1's hand and draft and see if it contains a card from Player 3's
        // last hand
        for (Card card : player3.getHand()) {
            String cardLetter = card.getLetter();

            // Check if the card's letter is in hand2before
            boolean foundInHand1 = false;
            for (Card otherCard : hand1before) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInHand1 = true;
                    break; // No need to continue checking if the letter is found
                }
            }
            boolean foundInPlayer1Draft = false;
            for (Card otherCard : player1.getDraft()) {
                if (cardLetter.equals(otherCard.getLetter())) {
                    foundInPlayer1Draft = true;
                    break; // No need to continue checking if the letter is found
                }
            }

            assertTrue("Card " + cardLetter + " not found in hand1before or player1's draft",
                    foundInHand1 || foundInPlayer1Draft);

        }

    }

    // Requirement 9. The final card is added to that player’s shown cards and is
    // known as the Catch card.
    @Test
    public void testShowLastCard() throws IOException {
        gameContext.setCurrentRound(1);
        gameContext.setRoundsToRun(7);
        mockGameLogic.getGameRules().passCards(players);
        for (int i = 0; i < 6; i++) {
            gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        }
        int currentPlayerID = 0;

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("Player ").append(currentPlayerID + 1).append(" has drafted\n")
                .append(mockGameLogic
                        .printCards(new ArrayList<>(player2.getDraft().subList(1, player2.getDraft().size()))))
                .append("\n");

        assertEquals(expectedMessage.toString(), mockGameLogic.generateDraftMessage(players, currentPlayerID));

    }

}

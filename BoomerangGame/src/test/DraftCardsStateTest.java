package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
import ltu.game.state.IGameState;
import ltu.game.state.InitRoundState;
import ltu.main.BoomerangGame;
import ltu.player.Player;
import ltu.player.actions.BotPlayerActionsStandard;
import ltu.player.actions.HumanPlayerActionsStandard;
import ltu.player.actions.IPlayerActions;

public class DraftCardsStateTest {

    DraftCardsState draftCardsState;
    GameLogic mockGameLogic;
    GameContext gameContext;
    Card[] cards;
    ArrayList<Player> players;

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
        Player player1 = new Player(0, playerActions);
        Player player2 = new Player(1, playerActions);
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
    }

    // Requirement 5. Select a Throw Card
    @Test
    public void testSelectedThrowCard() throws IOException {
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        for (Player p : players) {
            assertEquals(1, p.getDraft().size());
        }
    }

    // Requirement 5. Check that the Throw card is not revealed to other players
    @Test
    public void testHiddenThrowCard() throws IOException {
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        for (Player p : players) {
            assertEquals("", p.getDraft().size());
        }
    }

}

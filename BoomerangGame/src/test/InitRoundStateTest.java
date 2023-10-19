package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ltu.card.Card;
import ltu.game.logic.GameLogic;
import ltu.game.logic.GameLogicFactory;
import ltu.game.logic.IGameRules;
import ltu.game.scoring.IScoring;
import ltu.game.state.GameContext;
import ltu.game.state.InitRoundState;
import ltu.main.BoomerangGame;
import ltu.player.Player;
import ltu.player.actions.BotPlayerActionsStandard;
import ltu.player.actions.IPlayerActions;

/**
 * This class holds test for the InitRoundState class and test requirements 3-4
 */
public class InitRoundStateTest {
    InitRoundState initState;
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
        initState = new InitRoundState();
        IPlayerActions playerActions = new BotPlayerActionsStandard();
        players = new ArrayList<>();

        // Create a GameContext with players
        gameContext = new GameContext(players, null);
        Player player1 = new Player(0, playerActions);
        Player player2 = new Player(1, playerActions);
        players.add(player1);
        players.add(player2);
        gameContext = new GameContext(players, mockGameLogic);
        gameContext.setCurrentState(initState);
    }

    // Requirement 3. Shuffle the 28 cards.
    @Test
    public void testCardShuffling() throws IOException {
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        ArrayList<Card> player1Cards = players.get(0).getHand();

        boolean cardsOrderChanged = false;
        for (int i = 0; i < 7; i++) {
            if (cards[i] != player1Cards.get(i)) {
                cardsOrderChanged = true;
                break;
            }
        }

        assertTrue(cardsOrderChanged);
    }

    // 4. Deal 7 cards to each player.
    @Test
    public void testDealCards() throws IOException {
        gameContext.getCurrentState().executeAction(players, mockGameLogic, gameContext);
        for (Player p : players) {
            assertEquals(7, p.getHand().size());
        }

    }

}

package test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ltu.card.AustralianCard;
import ltu.game.logic.GameLogic;
import ltu.game.scoring.australiaScoring;
import ltu.card.Card;
import ltu.player.Player;

public class ScoringTest {
    private australiaScoring testAustraliaScoring = new australiaScoring();
    private ArrayList<Card> testDraft1 = null;
    private ArrayList<Card> testDraft2 = null;
    private String[] regions = { "Wester Australia", "Northern Territory", "Queensland", "South Australia",
            "New South Whales",
            "Victoria", "Tasmania" };

    private GameLogic gameLogic = new GameLogic(null, testAustraliaScoring, null, regions);
    private Player testPlayer1 = null;
    private Player testPlayer2 = null;
    ArrayList<Player> players;

    @Before
    public void initPlayer1() throws IOException {
        testPlayer1 = new Player(0, null);
        testDraft1 = new ArrayList<>(); // Initialize testDraft1
        testDraft1.add(
                new AustralianCard("The Whitsundays", "J", "Queensland", 6, "", "Kangaroos", "Indigenous Culture"));
        testDraft1.add(new AustralianCard("Lake Eyre", "N", "South Australia", 3, "", "Emus", "Swimming"));
        testDraft1
                .add(new AustralianCard("Margaret River", "C", "Western Australia", 1, "Shells", "Kangaroos", ""));
        testDraft1
                .add(new AustralianCard("Kalbarri National Park", "D", "Western Australia", 1, "Wildflowers", "",
                        "Bushwalking"));
        testDraft1
                .add(new AustralianCard("Uluru", "E", "Northern Territory", 4, "", "Emus", "Indigenous Culture"));
        testDraft1.add(
                new AustralianCard("Kakadu National Park", "F", "Northern Territory", 4, "", "Wombats", "Sightseeing"));

        testDraft1.add(
                new AustralianCard("Nitmiluk National Park", "G", "Northern Territory", 4, "Shells", "Platypuses", ""));
    }

    @Before
    public void initPlayer2() throws IOException {
        testPlayer2 = new Player(1, null);
        testAustraliaScoring = new australiaScoring();
        testDraft2 = new ArrayList<>(); // Initialize testDraft1
        testDraft2
                .add(new AustralianCard("The Bungle Bungles", "A", "Western Australia", 1, "Leaves", "",
                        "Indigenous Culture"));
        testDraft2.add(new AustralianCard("Lake Eyre", "N", "South Australia", 3, "", "Emus", "Swimming"));
        testDraft2.add(new AustralianCard("Royal Exhibition Building", "X", "Victoria", 2, "Leaves", "Platypuses", ""));
        testDraft2.add(new AustralianCard("Twelve Apostles", "W", "Victoria", 2, "Shells", "", "Swimming"));
        testDraft2.add(new AustralianCard("The MCG", "V", "Victoria", 2, "Leaves", "", "Indigenous Culture"));
        testDraft2
                .add(new AustralianCard("Melbourne", "U", "Victoria", 2, "", "Wombats", "Bushwalking"));
        testDraft2.add(
                new AustralianCard("The Whitsundays", "J", "Queensland", 6, "", "Kangaroos", "Indigenous Culture"));
    }

    @Before
    public void playerList() throws IOException {
        players = new ArrayList<Player>();
        players.add(0, testPlayer1);
        players.add(1, testPlayer2);
    }

    // Requirement 10a
    @Test
    public void testThrowCatchScore1() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(2, testAustraliaScoring.throwCatchScore(testPlayer1));
    }

    @Test
    public void testThrowCatchScore2() throws Exception {
        for (Card c : testDraft2) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(5, testAustraliaScoring.throwCatchScore(testPlayer1));
    }

    // Requirement 10b
    @Test
    public void testRoundSitesScoreNoSitesBefore() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }

        assertEquals(7, testAustraliaScoring.roundSitesScore(testPlayer1));
    }

    @Test
    public void testRoundSitesScoreSomeSitesBefore() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }
        testPlayer1.setSites("J", "Queensland");
        testPlayer1.setSites("N", "South Australia");
        assertEquals(5, testAustraliaScoring.roundSitesScore(testPlayer1));
    }

    // Requirement 10b(i+ii)
    @Test
    public void testRegionCompleteScoreNotcompleted() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }

        assertEquals(0, testAustraliaScoring.regionCompleteScore(testPlayer1, gameLogic));
    }

    @Test
    public void testRegionCompleteScoreCompleted() throws Exception {
        for (Card c : testDraft2) {
            testPlayer1.getDraft().add(c);
        }

        assertEquals(3, testAustraliaScoring.regionCompleteScore(testPlayer1, gameLogic));
    }

    @Test
    public void testRegionCompleteScoreCompletedButNotFirst() throws Exception {
        for (Card c : testDraft2) {
            testPlayer1.getDraft().add(c);
        }
        gameLogic.getFinishedRegions().add("Victoria");
        assertEquals(0, testAustraliaScoring.regionCompleteScore(testPlayer1, gameLogic));
    }

    // Requirement 10c
    @Test
    public void testCollectionsScoreAbove7() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(8, testAustraliaScoring.collectionsScore(testPlayer1));
    }

    @Test
    public void testCollectionsScoreUnder7() throws Exception {
        for (Card c : testDraft2) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(12, testAustraliaScoring.collectionsScore(testPlayer1));
    }

    // Requirement 10d
    @Test
    public void testAnimalScoreTwoPairs() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(7, testAustraliaScoring.animalScore(testPlayer1));
    }

    @Test
    public void testAnimalScoreNoPair() throws Exception {
        for (Card c : testDraft2) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(0, testAustraliaScoring.animalScore(testPlayer1));
    }

    // Requirement 10 e
    @Test
    public void testActivityScore1() throws Exception {
        for (Card c : testDraft1) {
            testPlayer1.getDraft().add(c);
        }
        assertEquals(2, testAustraliaScoring.additionalScore(testPlayer1, gameLogic));
    }

    @Test
    public void testActivityScore2() throws Exception {
        for (Card c : testDraft2) {
            testPlayer2.getDraft().add(c);
        }
        assertEquals(4, testAustraliaScoring.additionalScore(testPlayer2, gameLogic));
    }

    // Requirement 12. Game end. After scoring the fourth round, the game ends.
    // Players add up all their scores in each category, including the region
    // bonuses. The highest score wins.
    @Test
    public void testWinner() throws Exception {
        testPlayer1.setFinalScore(118);
        testPlayer2.setFinalScore(110);
        ArrayList<Player> players2 = new ArrayList<Player>();
        players2.add(0, testPlayer1);
        players2.add(0, testPlayer2);
        Player highScore = gameLogic.calculateWinner(players2);
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("The winner is player: " + 0
                + " with " + 118 + " points");

        assertEquals(expectedMessage.toString(), gameLogic.printOfWinner(highScore));
    }

    // Requirement 12 a. In the case of a tie, the tied player who scored the most
    // Throw & Catch points wins.
    /*
     * @Test
     * public void testWinnerTie() throws Exception {
     * testPlayer1.setFinalScore(110);
     * testPlayer2.setFinalScore(110);
     * HashMap<String, Integer> score1 = new HashMap<String, Integer>();
     * int throwCatchScoreP1 = Math
     * .abs(testPlayer1.getDraft().get(0).getNumber() -
     * testPlayer1.getDraft().get(6).getNumber());
     * testPlayer1.setFinalScore(testPlayer1.getFinalScore() + throwCatchScoreP1);
     * score1.put("Throw and Catch score", throwCatchScoreP1);
     * ArrayList<HashMap<String, Integer>> rScore1 = testPlayer1.getRScore();
     * rScore1.add(score1);
     * testPlayer1.setRScore(rScore1);
     * HashMap<String, Integer> score2 = new HashMap<String, Integer>();
     * int throwCatchScoreP2 = Math
     * .abs(testPlayer2.getDraft().get(0).getNumber() -
     * testPlayer2.getDraft().get(6).getNumber());
     * testPlayer2.setFinalScore(testPlayer1.getFinalScore() + throwCatchScoreP2);
     * score2.put("Throw and Catch score", throwCatchScoreP2);
     * ArrayList<HashMap<String, Integer>> rScore2 = testPlayer2.getRScore();
     * rScore2.add(score2);
     * testPlayer2.setRScore(rScore2);
     * Player highScore = gameLogic.calculateWinner(players);
     * StringBuilder expectedMessage = new StringBuilder();
     * expectedMessage.append("The winner is player: " + highScore.getPlayerID()
     * + " with " + highScore.getFinalScore() + " points");
     * 
     * assertEquals(expectedMessage.toString(), gameLogic.printOfWinner(highScore));
     * 
     * }
     */
}

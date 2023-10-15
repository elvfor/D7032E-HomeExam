package test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import card.AustralianCard;
import card.Card;
import game.logic.GameLogic;
import game.scoring.australiaScoring;
import player.Player;

public class ScoringTest {
    private australiaScoring testAustraliaScoring = new australiaScoring();
    private ArrayList<Card> testDraft1 = null;
    private ArrayList<Card> testDraft2 = null;
    private String[] regions = { "Wester Australia", "Northern Territory", "Queensland", "South Australia",
            "New South Whales",
            "Victoria", "Tasmania" };

    private GameLogic gameLogic = new GameLogic(null, testAustraliaScoring, null, regions);
    private Player testPlayer1 = null;

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

}

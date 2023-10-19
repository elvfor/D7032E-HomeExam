package ltu.player;

import java.util.ArrayList;
import java.util.HashMap;

import ltu.card.Card;
import ltu.player.actions.IPlayerActions;
import ltu.player.communication.IPlayerCommunication;

/**
 * This class represents the player who plays the game. Has abstractions of the
 * actions it can take during the game and abstraction of hpw it communicates
 * with the game. Also holds its own scoring points
 */
public class Player {
    private IPlayerActions playerActions;
    private int playerID;
    private ArrayList<String> region = new ArrayList<String>();
    private ArrayList<Card> nextPlayersHand = new ArrayList<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> draft = new ArrayList<Card>();
    HashMap<String, String> sites = new HashMap<String, String>(); // letter, region
    ArrayList<HashMap<String, Integer>> rScore = new ArrayList<HashMap<String, Integer>>();
    HashMap<String, Integer> activitiesScore = new HashMap<>();
    private int regionRoundScore = 0;
    private int finalScore = 0;
    private IPlayerCommunication playerCommunication;

    public Player(int playerID, IPlayerActions playerActions) {
        this.playerID = playerID;
        this.playerActions = playerActions;
    }

    public int getPlayerID() {
        return playerID;
    }

    public IPlayerActions getPlayerActions() {
        return playerActions;
    }

    public void setRegionRoundScore(int score) {
        regionRoundScore = score;
    }

    public void setRScore(ArrayList<HashMap<String, Integer>> rScore) {
        this.rScore = rScore;
    }

    public void setRoundScore(int score) {
        finalScore = score;
    }

    public ArrayList<String> getRegion() {
        return region;
    }

    public ArrayList<Card> getNextPlayersHand() {
        return nextPlayersHand;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDraft() {
        return draft;
    }

    public HashMap<String, String> getSites() {
        return sites;
    }

    public ArrayList<HashMap<String, Integer>> getRScore() {
        return rScore;
    }

    public HashMap<String, Integer> getActivitiesScore() {
        return activitiesScore;
    }

    public void addToActivitiesScore(String activity, int score) {
        this.activitiesScore.put(activity, score);
    }

    public int getRegionRoundScore() {
        return regionRoundScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int score) {
        this.finalScore = score;
    }

    public void setSites(String letter, String region) {
        this.sites.put(letter, region);
    }

    public void setPlayerCommunication(IPlayerCommunication playerCommunication) {
        this.playerCommunication = playerCommunication;
    }

    public IPlayerCommunication getPlayerCommunication() {
        return playerCommunication;
    }
}

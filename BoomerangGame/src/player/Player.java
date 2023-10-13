package player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import card.Card;
import player.actions.IPlayerActions;

public class Player {
	private IPlayerActions playerActions;
    private int playerID;
	private ArrayList<String> region = new ArrayList<String>();
	private ArrayList<Card> nextPlayersHand = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> draft = new ArrayList<Card>();
	HashMap<String, String> sites = new HashMap<String, String>(); //letter, region
	ArrayList<HashMap<String, Integer>> rScore = new ArrayList<HashMap<String, Integer>>();
	HashMap<String, Integer> activitiesScore = new HashMap<>();
	private int regionRoundScore = 0;
	private int finalScore = 0;

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
}

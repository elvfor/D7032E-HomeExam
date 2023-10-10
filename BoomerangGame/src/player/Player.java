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
	private ArrayList<Card> nextPLayersHand = new ArrayList<Card>();
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

    public ArrayList<String> getRegion() {
        return region;
    }

    public ArrayList<Card> getNextPlayersHand() {
        return nextPLayersHand;
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

    public int getRegionRoundScore() {
        return regionRoundScore;
    }

    public int getFinalScore() {
        return finalScore;
    }
}

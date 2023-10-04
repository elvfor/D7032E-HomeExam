package player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import card.Card;

public class Player {
    public int playerID;
	    public ArrayList<String> region = new ArrayList<String>();
	    Scanner in = new Scanner(System.in);
	    public ArrayList<Card> nextHand = new ArrayList<Card>();
	    public ArrayList<Card> hand = new ArrayList<Card>();
		public ArrayList<Card> draft = new ArrayList<Card>();
		HashMap<String, String> sites = new HashMap<String, String>(); //letter, region
		ArrayList<HashMap<String, Integer>> rScore = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> activitiesScore = new HashMap<>();
		public int regionRoundScore = 0;
		public int finalScore = 0;

    public Player(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
	    this.playerID = playerID;
	}
}

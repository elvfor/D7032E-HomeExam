package player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import player.Player;
public class HumanPlayer extends Player{
    //public Socket connection;
    Scanner in = new Scanner(System.in);
	public ObjectInputStream inFromClient;
	public ObjectOutputStream outToClient;
    public HumanPlayer(int playerID, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(playerID);
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

}
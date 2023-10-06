package player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import player.Player;
public class HumanPlayer extends Player{
    public Socket connection;
	public ObjectInputStream inFromClient;
	public ObjectOutputStream outToClient;
    public HumanPlayer(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(playerID);
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

}


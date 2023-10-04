package player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import player.Player;
public class HumanPlayer extends Player{

    public HumanPlayer(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(playerID, connection, inFromClient, outToClient);
        //TODO Auto-generated constructor stub
    }

}


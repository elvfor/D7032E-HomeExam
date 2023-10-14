package player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import player.Player;
import player.actions.IPlayerActions;
import player.communication.IPlayerCommunication;

public class HumanPlayer extends Player {
    // public Socket connection;
    public HumanPlayer(int playerID, IPlayerActions playerActions, IPlayerCommunication playerCommunication) {
        super(playerID, playerActions);
        setPlayerCommunication(playerCommunication);
    }
}
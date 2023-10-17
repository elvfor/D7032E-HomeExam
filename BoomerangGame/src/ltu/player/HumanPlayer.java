package ltu.player;

import ltu.player.actions.IPlayerActions;
import ltu.player.communication.IPlayerCommunication;

public class HumanPlayer extends Player {
    // public Socket connection;
    public HumanPlayer(int playerID, IPlayerActions playerActions, IPlayerCommunication playerCommunication) {
        super(playerID, playerActions);
        setPlayerCommunication(playerCommunication);
    }
}
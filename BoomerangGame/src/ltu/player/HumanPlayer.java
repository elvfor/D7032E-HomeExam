package ltu.player;

import ltu.player.actions.IPlayerActions;
import ltu.player.communication.IPlayerCommunication;

/**
 * This class represents the human players that play the game, extends the
 * Player class and has an added IPlayercommunication
 */
public class HumanPlayer extends Player {
    // public Socket connection;
    public HumanPlayer(int playerID, IPlayerActions playerActions, IPlayerCommunication playerCommunication) {
        super(playerID, playerActions);
        setPlayerCommunication(playerCommunication);
    }
}
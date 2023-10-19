package ltu.player.communication;

/**
 * This interface handles how each player communicates with for example the
 * game. Inject each player that should be able to communicate with game with a
 * class that implements this interface
 */
public interface IPlayerCommunication {
    // Send a message to the player
    void sendMessage(String message);

    // Receive input (text) from the player
    String receiveInput();
}

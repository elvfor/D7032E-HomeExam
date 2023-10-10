package player.communication;

public interface IPlayerCommunication {
    // Send a message to the player
    void sendMessage(String message);

    // Receive input (text) from the player
    String receiveInput();
}

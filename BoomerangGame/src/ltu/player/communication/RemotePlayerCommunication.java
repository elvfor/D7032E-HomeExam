package ltu.player.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * This class represents how a remote player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class RemotePlayerCommunication implements IPlayerCommunication {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public RemotePlayerCommunication(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param message Takes the message the game has sent to the player
     */
    @Override
    public void sendMessage(String message) {
        try {
            outputStream.writeObject(message);
        } catch (Exception e) {
        }
    }

    /**
     * @return String The string that the player sends for the game to recive as
     *         input
     */
    @Override
    public String receiveInput() {
        String word = "";
        try {
            return word = (String) inputStream.readObject();
        } catch (Exception e) {
        }
        return word;
    }

}

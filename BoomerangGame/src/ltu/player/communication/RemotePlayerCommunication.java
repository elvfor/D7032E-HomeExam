package ltu.player.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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

    @Override
    public void sendMessage(String message) {
        try {
            outputStream.writeObject(message);
        } catch (Exception e) {
        }
    }

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

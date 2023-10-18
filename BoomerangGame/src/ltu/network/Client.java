package ltu.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import ltu.exception.ClientConnectionException;

public class Client {

    public Client(String ipAddress) throws ClientConnectionException, ClassNotFoundException {
        try {
            // Connect to the server
            Socket aSocket = new Socket(ipAddress, 2048);
            ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
            String nextMessage = "";
            while (!nextMessage.contains("winner")) {
                nextMessage = (String) inFromServer.readObject();
                System.out.println(nextMessage);
                if (nextMessage.contains("Type") || nextMessage.contains("keep") || nextMessage.contains("choose")) {
                    Scanner in = new Scanner(System.in);
                    outToServer.writeObject(in.nextLine());
                }
            }
            aSocket.close();
            outToServer.close();
            inFromServer.close();
        } catch (IOException e) {
            throw new ClientConnectionException("Failed to connect /lost the connection to the server.", e);
        }
    }

}
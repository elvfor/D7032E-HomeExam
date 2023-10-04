package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final ObjectOutputStream outToServer;
    private final ObjectInputStream inFromServer;

    public Client(String ipAddress) throws Exception {
        //Connect to server
        Socket aSocket = new Socket(ipAddress, 2048);
        outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        System.out.println("Connected to server at IP " + ipAddress);

        while(!nextMessage.contains("winner")){
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if(nextMessage.contains("Type") || nextMessage.contains("keep")) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            } 
        }
    }
    public Client(Socket socket) throws IOException {
        try{
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            inFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
        throw new IOException();
        }
    }
}
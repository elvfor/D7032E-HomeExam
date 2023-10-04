package network;

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
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        while(!nextMessage.contains("winner")){
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if(nextMessage.contains("Type") || nextMessage.contains("keep")) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            } 
        }
    }
    public Client(){

    }
}

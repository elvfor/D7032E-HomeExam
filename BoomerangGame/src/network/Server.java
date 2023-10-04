package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import network.Client;

public class Server {
    private ServerSocket serverSocket;
    private final List<Client> connectedClients = new ArrayList<>();

    public Server(int port){
    try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptClient() {
        try {
            Socket connectionSocket = serverSocket.accept();
            Client client = new network.Client(connectionSocket);
            connectedClients.add(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getConnectedClients() {
        return connectedClients;
    }

}

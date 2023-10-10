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
            //Client client = Client(connectionSocket);
            //connectedClients.add(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void manageNewConnection(){
        Client newClient;
        try {
            Socket socket = serverSocket.accept();
            newClient = Client(socket);
            clients.add(newClient);
        } catch (IOException e) {
        }

        sendConnectionMessage(newClient);
    }
    public List<Client> getConnectedClients() {
        return connectedClients;
    }*/

    public void disconnectClients() {
        for (Client client : connectedClients) {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
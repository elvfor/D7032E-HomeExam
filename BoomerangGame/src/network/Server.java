package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<Socket> acceptedSockets = new ArrayList<>();

    public Server(int port, int numClients) {
        try {
            serverSocket = new ServerSocket(port);

            for (int i = 0; i < numClients; i++) {
                Socket clientSocket = serverSocket.accept();
                acceptedSockets.add(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Socket> getAcceptedSockets() {
        return acceptedSockets;
    }

    public void disconnectAllClients() {
        System.out.println("Disconnecting connected Clients");
        for (Socket socket : acceptedSockets) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        acceptedSockets.clear();
    }

}
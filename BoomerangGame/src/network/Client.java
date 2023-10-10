package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final ObjectOutputStream outToServer;
    private final ObjectInputStream inFromServer;
    private final Socket connectionSocket;

    public Client(ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket connectionSocket){
        this.inFromServer = inputStream;
        this.outToServer = outputStream;
        this.connectionSocket = connectionSocket;
    }

    public Client(String ipAddress) throws Exception {
        //Connect to server
        connectionSocket = new Socket(ipAddress, 2048);
        outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
        inFromServer = new ObjectInputStream(connectionSocket.getInputStream());
        System.out.println("Connected to server at IP " + ipAddress);
        //remotePlayerEnterGameLoop(null);
    }

    public Client Client(Socket socket) throws IOException {
        try{
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
            return new Client(outToServer, inFromServer, socket);
        } catch (IOException e) {
        throw new IOException();
        }
    }
    public ObjectInputStream getInFromServer() {
        return inFromServer;
    }

    public ObjectOutputStream getOutToServer() {
        return outToServer;
    }
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    public void disconnect() throws IOException {
        if (outToServer != null) {
            outToServer.close();
        }
        if (inFromServer != null) {
            inFromServer.close();
        }
        if (connectionSocket != null) {
            connectionSocket.close();
        }
    }

}
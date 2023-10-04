import java.net.Socket;
import java.util.List;

import network.Client;
import network.Server;
import java.net.ServerSocket;

public class BoomerangGame{

    public static void main(String[] args) throws Exception {
        if(args.length == 1) {
            String ipAddress = args[0];
            Client client = new network.Client(ipAddress);
        }
        else{
            int nrOfPlayers = Integer.valueOf(args[0]).intValue();
            int nrOfbots = Integer.valueOf(args[1]).intValue();
            try{
            Object players = connectionSetup(nrOfPlayers, nrOfbots);

            } catch(Exception e) {
        }
        }
    }
    private static List<Client> connectionSetup(int nrOfPlayers, int nrOfbots) throws Exception{
        //Requirement 1, 2-4 players
		if(((nrOfPlayers+nrOfbots)>=2) && ((nrOfPlayers+nrOfbots)<=4)) {
			Server server = new network.Server(2048);
            System.out.println("Server is listening on port 2048");
             for (int i = 0; i < nrOfPlayers; i++) {
            server.acceptClient();
        }
        // Get the list of connected clients
        List<Client> connectedClients = server.getConnectedClients();
		return connectedClients;
		} else {
			System.out.println("This game is for a total of 2-4 players/bots");
			System.out.println("Server syntax: java BoomerangAustralia numPlayers numBots");
			System.out.println("Client syntax: IP");
		}
        return null;
    }

    //private static createPlayers()

}


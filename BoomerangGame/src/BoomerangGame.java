import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import card.Card;
import card.CardFactory;
import network.Client;
import network.Server;
import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class BoomerangGame{

    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                String ipAddress = args[0];
                Client client = new Client(ipAddress);
            } else if (args.length == 2) {
                int nrOfPlayers = Integer.parseInt(args[0]);
                int nrOfBots = Integer.parseInt(args[1]);

                List<Client> connectedClients = connectionSetup(nrOfPlayers, nrOfBots);
                List<Player> players = createPlayers(nrOfPlayers, nrOfBots, connectedClients);

                // Assuming you have a method to load the cards from a JSON config file
                List<Card> cards = createCardsFromConfig("C:\\Users\\elvir\\D7032E-HomeExam\\BoomerangGame\\config\\australianCards.JSON");
            } else {
                System.err.println("Invalid number of command-line arguments.");
                System.err.println("Usage: java BoomerangGame <ipAddress> OR java BoomerangGame <numPlayers> <numBots>");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    private static List<Player> createPlayers(int nrOfPlayers, int nrOfBots, List<Client> clients) {
    List<Player> players = new ArrayList<>();

    for (int i = 0; i < nrOfPlayers; i++) {
        if (i < clients.size()) {
            Socket connection = clients.get(i).getConnectionSocket();
            ObjectInputStream inFromClient = clients.get(i).getInFromServer();
            ObjectOutputStream outToClient = clients.get(i).getOutToServer();

            // Create HumanPlayer instances for human players
            HumanPlayer humanPlayer = new HumanPlayer(i + 1, connection, inFromClient, outToClient);
            players.add(humanPlayer);
        } else {
            // Handle the case where you don't have enough clients for human players
            // You might want to log an error or take some other action here.
        }
    }

    for (int i = nrOfPlayers ; i < nrOfBots; i++) {
        // Create BotPlayer instances for bot players
        BotPlayer botPlayer = new BotPlayer(i + 1); // You need to implement BotPlayer
        players.add(botPlayer);
    }

    return players;
    }
    private static List<Card> createCardsFromConfig(String configFilePath) {
        try {
            // Read the JSON config file
            FileReader fileReader = new FileReader(configFilePath);
            JsonObject config = JsonParser.parseReader(fileReader).getAsJsonObject();
            fileReader.close();

            // Extract the version from the JSON
            String version = config.get("version").getAsString();
            JsonArray cardsData= (JsonArray) config.get("cards");
            // Create cards based on the version
            CardFactory cardFactory = new CardFactory();
            List<Card> cards = cardFactory.createCardFromConfig(cardsData, version);
            return cards;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


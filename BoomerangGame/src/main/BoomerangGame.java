package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import card.AustralianCard;
import card.Card;
import card.CardFactory;
import game.GameFactory;
import game.rules.IRules;
import game.scoring.IScoring;
import game.state.GameContext;
import game.state.IGameState;
import game.state.StartGame;
import network.Client;
import network.Server;
import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

public class BoomerangGame{

    private static final IGameState GameOverState = null;
    //private Set<Player> players = null;
    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                String ipAddress = args[0];
                //join as remote player, enter loop for game
                Client client = new Client(ipAddress);
                remotePlayerGameLoop(client);
            } else if (args.length == 2) {
                int nrOfPlayers = Integer.parseInt(args[0]);
                int nrOfBots = Integer.parseInt(args[1]);

                if(checkNrOfPlayerReq(nrOfPlayers, nrOfBots));
                //List<Client> connectedClients = connectionSetup(nrOfPlayers, nrOfBots);
                List<Player> players = new ArrayList<>();
                for(int i = 0; i<nrOfPlayers ; i++){
                    HumanPlayer player = new HumanPlayer(i, null, null);
                    players.add(player);
                }
                for(int i = nrOfPlayers ; i<nrOfPlayers+nrOfBots ; i++){
                    BotPlayer player = new BotPlayer(i);
                    players.add(player);
                }
                GameContext context = initGameContext(players, "Australia", "Standard");
                startGame(context);
            } else {
                System.err.println("Invalid number of command-line arguments.");
                System.err.println("Usage: java BoomerangGame <ipAddress> OR java BoomerangGame <numPlayers> <numBots>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean checkNrOfPlayerReq(int nrOfPlayers, int nrOfbots) throws Exception{
        //Requirement 1, 2-4 players
		if(((nrOfPlayers+nrOfbots)>=2) && ((nrOfPlayers+nrOfbots)<=4)) {
			return true;
        }else {
			System.out.println("This game is for a total of 2-4 players/bots");
			System.out.println("Server syntax: java BoomerangAustralia numPlayers numBots");
			System.out.println("Client syntax: IP");
		}
        return false;
    }

    /*private static List<Client> connectionSetup(int nrOfPlayers, int nrOfbots) throws Exception{
			Server server = new network.Server(2048);
            System.out.println("Server is listening on port 2048");
            for (int i = 1; i < nrOfPlayers; i++) {
            server.acceptClient();
        }
        // Get the list of connected clients
        System.out.println("test2");
        List<Client> connectedClients = server.getConnectedClients();
        
		return connectedClients;
    }*/

    private static GameContext initGameContext(List<Player> players, String version, String rules){
        IRules gameRules = GameFactory.createGameRules(rules);
        IScoring scoring = GameFactory.createScoring(version);
        GameContext context = new GameContext();
        context.setCards(createCards(version));
        context.setPlayers(players);
        context.setCurrentState(new StartGame());
        context.setRules(gameRules);
        context.setScoring(scoring);
        return context;
    }

    private static void startGame(GameContext context) throws IOException{
        System.out.println("test1");

        while (context.getCurrentState() != GameOverState) {
            // Execute the current state's action
            System.out.println("test3");
            context.getCurrentState().executeAction(context);
        }
    }
    private static List<Card> createCards(String version){
        CardFactory cardFactory = new CardFactory();
        List<Card> cards;
        System.out.println("test");
        try {
            cards = cardFactory.createCards(version);
            for (Card card : cards) {
            System.out.println("Card Name: " + card.getName());
            System.out.println("Letter: " + card.getLetter());
            System.out.println("Region: " + card.getRegion());
            System.out.println("Number: " + card.getNumber());
            // Check if the card is an instance of AustralianCard
            if (card instanceof AustralianCard) {
                AustralianCard australianCard = (AustralianCard) card;
                System.out.println("Collections: " + australianCard.getCollection());
                System.out.println("Animals: " + australianCard.getAnimal());
                System.out.println("Activities: " + australianCard.getActivity());
            }
            // Print other card details as needed
            System.out.println("--------------------------");
        }
            return cards;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void remotePlayerGameLoop(Client client){
        String nextMessage = "";
        ObjectInputStream inFromServer = client.getInFromServer();
        ObjectOutputStream outToServer = client.getOutToServer();
        while (true) {
            try {
                nextMessage = (String) inFromServer.readObject();
                System.out.println(nextMessage);
                
                if (nextMessage.contains("Type") || nextMessage.contains("keep")) {
                    Scanner in = new Scanner(System.in);
                    String userInput = in.nextLine();
                    outToServer.writeObject(userInput);
                }
            } catch (IOException e) {
                // Handle the exception, e.g., connection lost
                System.out.println("Connection to the server is lost.");
                break; // Exit the loop if an exception occurs
            } catch (ClassNotFoundException e) {
                // Handle the exception, e.g., unexpected object type
                System.err.println("Error: Unexpected object received from server.");
            }
        }
    }
}
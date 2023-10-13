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
import game.gameContext.GameContext;
import game.logic.GameLogic;
import game.logic.GameLogicFactory;
import game.logic.IGameRules;
import game.scoring.IScoring;
import game.state.IGameState;
import network.Client;
import network.Server;
import player.BotPlayer;
import player.HumanPlayer;
import player.Player;
import player.actions.IPlayerActions;
import player.actions.BotPlayerActionsStandard;
import player.actions.HumanPlayerActionsStandard;
import player.communication.IPlayerCommunication;
import player.communication.LocalPlayerCommunication;

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
                ArrayList<Player> players = new ArrayList<>();
                for(int i = 0; i<nrOfPlayers ; i++){
                    IPlayerActions playerActions = new HumanPlayerActionsStandard();
                    IPlayerCommunication playerCommunication = new LocalPlayerCommunication();
                    HumanPlayer player = new HumanPlayer(i, playerActions, playerCommunication);
                    players.add(player);
                }
                for(int i = nrOfPlayers ; i<nrOfPlayers+nrOfBots ; i++){
                    IPlayerActions botPlayerActions = new BotPlayerActionsStandard();

                    BotPlayer player = new BotPlayer(i, botPlayerActions);
                    players.add(player);
                }
                GameLogic gameLogic = initGameLogic("Australia", "Standard");
                GameContext gameContext = initGameContext(players, gameLogic);
                //startGame(gameContext);
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
        return pls
		return connectedClients;
    }*/

    private static GameContext initGameContext(ArrayList<Player> players, GameLogic gameLogic){
        return new GameContext(players, gameLogic);
    }

    private static GameLogic initGameLogic( String version, String rules){
        IGameRules gameRules = GameLogicFactory.createGameRules(rules);
        IScoring scoring = GameLogicFactory.createScoring(version);
        Card[] cards = createCards(version);
        String[] regions = createRegions(version);
        return new GameLogic(gameRules, scoring, cards, regions);
    }

    /*private static void startGame(GameContext context) throws IOException{
        System.out.println("test1");

        while (context.getCurrentState() != GameOverState) {
            // Execute the current state's action
            System.out.println("test3");
            context.getCurrentState().executeAction(context);
        }
    }*/
    private static Card[] createCards(String version){
        CardFactory cardFactory = new CardFactory();
        Card[] cards;
        try {
            cards = cardFactory.createCards(version);
            return cards;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String[] createRegions(String version){
        CardFactory cardFactory = new CardFactory();
        String[] regions;
        try {
            regions = cardFactory.createRegionsFromConfig(version);
            return regions;
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
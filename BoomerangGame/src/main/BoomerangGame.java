package main;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import card.Card;
import card.CardFactory;
import card.ICardFactory;
import game.logic.GameLogic;
import game.logic.GameLogicFactory;
import game.logic.IGameRules;
import game.scoring.IScoring;
import game.state.GameContext;
import game.state.InitRoundState;
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
import player.communication.RemotePlayerCommunication;

public class BoomerangGame {
    public BoomerangGame(String[] args) throws Exception {
        try {
            if (args.length == 1) {
                playAsRemotePlayer(args);
            } else if (args.length == 2) {
                int nrOfPlayers = Integer.parseInt(args[0]);
                int nrOfBots = Integer.parseInt(args[1]);
                if (checkNrOfPlayerReq(nrOfPlayers, nrOfBots)) {
                    System.out.print("Initializing game with " + (nrOfPlayers) + " players and " + (nrOfBots) + "\n");
                    playAsLocalPlayer(nrOfPlayers, nrOfBots);
                } else {
                    System.out.println("This game is for a total of 2-4 players/bots");
                    System.out.println("Server syntax: java BoomerangAustralia numPlayers numBots");
                    System.out.println("Client syntax: IP");
                }
            } else {
                System.err.println("Invalid number of command-line arguments.");
                System.err
                        .println("Usage: java BoomerangGame <ipAddress> OR java BoomerangGame <numPlayers> <numBots>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playAsRemotePlayer(String[] args) throws Exception {
        String ipAddress = args[0];
        Client client = new Client(ipAddress);
    }

    private static void playAsLocalPlayer(int nrOfPlayers, int nrOfBots) throws IOException {
        setUpGame(nrOfPlayers, nrOfBots);
    }

    public static boolean checkNrOfPlayerReq(int nrOfPlayers, int nrOfbots) throws Exception {
        // Requirement 1, 2-4 players
        if (((nrOfPlayers + nrOfbots) >= 2) && ((nrOfPlayers + nrOfbots) <= 4)) {
            return true;
        } else {
            return false;
        }
    }

    private static void setUpGame(int nrOfPlayers, int nrOfBots) throws IOException {
        ArrayList<Player> players = new ArrayList<>();
        addLocalPlayer(players);
        addRemotePlayers(players, nrOfPlayers);
        addBotPlayers(players, nrOfPlayers, nrOfBots);
        String version = chooseGameMode(players.get(0));
        String rules = chooseGameRules(players.get(0));
        GameLogic gameLogic = initGameLogic(version, rules);
        GameContext gameContext = new GameContext(players, gameLogic);
        startGame(gameContext);
    }

    private static String chooseGameMode(Player player) {
        String response = "";
        player.getPlayerCommunication().sendMessage("Game Mode Australia? Y/N");
        response = player.getPlayerCommunication().receiveInput();
        if ("Y".equalsIgnoreCase(response)) {
            return "Australia";
        } else {
            player.getPlayerCommunication()
                    .sendMessage("Currently no other Game Modes avaliable. Automatically set as Australia");
            return "Australia";
        }
    }

    private static int chooseServerPort(Player player) {
        String response = "";
        player.getPlayerCommunication().sendMessage("Use standard port (2048) for remote player connection? Y/N");
        response = player.getPlayerCommunication().receiveInput();
        if ("Y".equalsIgnoreCase(response)) {
            return 2048;
        } else {
            player.getPlayerCommunication().sendMessage("Enter port number for remote player connection:");
            response = player.getPlayerCommunication().receiveInput();
            try {
                return Integer.parseInt(response);
            } catch (NumberFormatException e) {
                player.getPlayerCommunication().sendMessage("Invalid port number. Using standard port (2048).");
                return 2048;
            }
        }
    }

    private static String chooseGameRules(Player player) {
        String response = "";
        player.getPlayerCommunication().sendMessage("Standard Game Rules Y/N");
        response = player.getPlayerCommunication().receiveInput();
        if ("Y".equalsIgnoreCase(response)) {
            return "Standard";
        } else {
            player.getPlayerCommunication()
                    .sendMessage("Currently no other rules avaliable. Automatically set as Standard");
            return "Standard";
        }
    }

    private static void addLocalPlayer(ArrayList<Player> players) {
        IPlayerActions playerActions = new HumanPlayerActionsStandard();
        IPlayerCommunication playerCommunication = new LocalPlayerCommunication();
        HumanPlayer player = new HumanPlayer(0, playerActions, playerCommunication);
        players.add(player);
    }

    private static void addRemotePlayers(ArrayList<Player> players, int nrOfPlayers) {
        if (nrOfPlayers > 1) {
            int port = chooseServerPort(players.get(0));
            players.get(0).getPlayerCommunication()
                    .sendMessage("Waiting for " + (nrOfPlayers - 1) + " players to join.");
            Server server = new Server(port, nrOfPlayers - 1);
            List<Socket> acceptedSockets = server.getAcceptedSockets();
            int numAcceptedSockets = acceptedSockets.size();
            for (int i = 1; i < nrOfPlayers; i++) {
                Socket acceptedSocket = acceptedSockets.get(i % numAcceptedSockets); // Use the sockets in a round-robin
                IPlayerActions playerActions = new HumanPlayerActionsStandard();
                IPlayerCommunication playerCommunication = new RemotePlayerCommunication(acceptedSocket);
                HumanPlayer player = new HumanPlayer(i, playerActions, playerCommunication);
                players.add(player);
            }
        }
    }

    private static void addBotPlayers(ArrayList<Player> players, int nrOfPlayers, int nrOfBots) {
        for (int i = nrOfPlayers; i < nrOfPlayers + nrOfBots; i++) {
            IPlayerActions botPlayerActions = new BotPlayerActionsStandard();
            BotPlayer player = new BotPlayer(i, botPlayerActions);
            players.add(player);
        }
    }

    private static GameLogic initGameLogic(String version, String rules) {
        IGameRules gameRules = GameLogicFactory.createGameRules(rules);
        IScoring scoring = GameLogicFactory.createScoring(version);
        Card[] cards = createCards(version);
        String[] regions = createRegions(version);
        return new GameLogic(gameRules, scoring, cards, regions);
    }

    private static void startGame(GameContext gameContext) throws IOException {
        gameContext.setCurrentState(new InitRoundState());
        gameContext.startGame();
        endGame();
    }

    private static void endGame() {
        System.out.println("Game is Over");
        System.exit(0);
    }

    private static Card[] createCards(String version) {
        CardFactory cardFactory = new CardFactory();
        ICardFactory cardCreator = cardFactory.getCardFactory(version);
        Card[] cards;
        try {
            cards = cardCreator.createCards();
            return cards;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] createRegions(String version) {
        CardFactory cardFactory = new CardFactory();
        ICardFactory cardCreator = cardFactory.getCardFactory(version);
        String[] regions;
        try {
            regions = cardCreator.createRegions();
            return regions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String argv[]) {
        try {
            new BoomerangGame(argv);
        } catch (Exception e) {

        }
    }
}
package ltu.main;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ltu.game.logic.GameLogic;
import ltu.card.Card;
import ltu.card.CardFactory;
import ltu.card.ICardFactory;
import ltu.exception.ClientConnectionException;
import ltu.exception.InvalidGameModeException;
import ltu.exception.InvalidGameRuleException;
import ltu.exception.InvalidNrOfPlayersException;
import ltu.game.logic.GameLogicFactory;
import ltu.game.logic.IGameRules;
import ltu.game.scoring.IScoring;
import ltu.game.state.GameContext;
import ltu.game.state.InitRoundState;
import ltu.network.Server;
import ltu.player.BotPlayer;
import ltu.player.HumanPlayer;
import ltu.player.Player;
import ltu.player.actions.HumanPlayerActionsStandard;
import ltu.player.actions.IPlayerActions;
import ltu.player.communication.LocalPlayerCommunication;
import ltu.player.communication.RemotePlayerCommunication;
import ltu.network.Client;
import ltu.player.actions.BotPlayerActionsStandard;
import ltu.player.communication.IPlayerCommunication;

public class BoomerangGame {
    public BoomerangGame(String[] args) throws ClientConnectionException, IOException {
        if (args.length == 1) {
            playAsRemotePlayer(args);
        } else if (args.length == 2) {
            int nrOfPlayers = Integer.parseInt(args[0]);
            int nrOfBots = Integer.parseInt(args[1]);
            try {
                initializeGame(nrOfPlayers, nrOfBots);
            } catch (InvalidNrOfPlayersException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Server syntax: java BoomerangAustralia numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }

    private void initializeGame(int nrOfPlayers, int nrOfBots) throws InvalidNrOfPlayersException, IOException {
        if (checkNrOfPlayerReq(nrOfPlayers, nrOfBots)) {
            System.out.println("Initializing game with " + nrOfPlayers + " players and " + nrOfBots);
            playAsLocalPlayer(nrOfPlayers, nrOfBots);
        } else {
            throw new InvalidNrOfPlayersException("Invalid number of players. This game is for 2-4 players/bots.");
        }
    }

    private static void playAsRemotePlayer(String[] args) throws ClientConnectionException {
        String ipAddress = args[0];
        try {
            Client client = new Client(ipAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playAsLocalPlayer(int nrOfPlayers, int nrOfBots) throws IOException {
        setUpGame(nrOfPlayers, nrOfBots);
    }

    public static boolean checkNrOfPlayerReq(int nrOfPlayers, int nrOfbots) {
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
        String version;
        try {
            version = chooseGameMode(players.get(0));
        } catch (InvalidGameModeException e) {
            System.err.println("Error: " + e.getMessage());

            version = "Australia"; // for now, set default version
        }
        String rules;
        try {
            rules = chooseGameRules(players.get(0));
        } catch (InvalidGameRuleException e) {
            System.err.println("Error: " + e.getMessage());

            rules = "Standard"; // for now, set default version
        }
        GameLogic gameLogic = initGameLogic(version, rules);
        GameContext gameContext = new GameContext(players, gameLogic);
        startGame(gameContext);
    }

    private static String chooseGameMode(Player player) throws InvalidGameModeException {
        while (true) {
            String response = "";
            player.getPlayerCommunication().sendMessage("Game Mode Australia? Y/N");
            response = player.getPlayerCommunication().receiveInput();

            if ("Y".equalsIgnoreCase(response)) {
                return "Australia";
            } else if ("N".equalsIgnoreCase(response)) {
                throw new InvalidGameModeException(
                        "Currently no other Game Modes available. Automatically set as Australia");
            } else {
                // Invalid input, prompt to choose again
                player.getPlayerCommunication().sendMessage("Invalid input. Please choose 'Y' or 'N'.");
            }
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

    private static String chooseGameRules(Player player) throws InvalidGameRuleException {
        while (true) {
            String response = "";
            player.getPlayerCommunication().sendMessage("Standard Game Rules Y/N");
            response = player.getPlayerCommunication().receiveInput();
            if ("Y".equalsIgnoreCase(response)) {
                return "Standard";
            } else if ("N".equalsIgnoreCase(response)) {
                throw new InvalidGameRuleException(
                        "Currently no other Game Rules available. Automatically set as Standard");
            } else {
                // Invalid input, prompt to choose again
                player.getPlayerCommunication().sendMessage("Invalid input. Please choose 'Y' or 'N'.");
            }
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

    public static Card[] createCards(String version) {
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

    public static String[] createRegions(String version) {
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
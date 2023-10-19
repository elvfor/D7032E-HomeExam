package ltu.game.logic;

import java.util.ArrayList;
import java.util.Arrays;

import ltu.card.Card;
import ltu.player.Player;
import ltu.game.scoring.IScoring;

/**
 * This class is responsible for the logic that the game requires to function.
 * Has abstreactions of the rules and socring as well as generic functions used
 * for all
 * modes, all rules and scoring
 */
public class GameLogic {
    private IGameRules gameRules;
    private IScoring scoring;
    private Card[] cards;
    private String[] regions;
    private ArrayList<String> finishedRegions = new ArrayList<String>();

    public GameLogic(IGameRules gameRules, IScoring scoring, Card[] cards, String[] regions) {
        this.gameRules = gameRules;
        this.scoring = scoring;
        this.cards = cards;
        this.regions = regions;
    }

    public void printCurrentDraft(Player player) {
        if (player.getDraft().size() == 0) {
            if (player.getPlayerCommunication() != null) {
                player.getPlayerCommunication().sendMessage("You haven't drafted any cards yet");
            }
        } else {
            if (player.getPlayerCommunication() != null) {
                player.getPlayerCommunication().sendMessage(
                        "\n*****************************\nYour current draft: \n"
                                + printCards(player.getDraft()));
            }
        }
    }

    public ArrayList<Card> getDeck(ArrayList<Player> players) {
        ArrayList<Card> deck = new ArrayList<>(Arrays.asList(cards));
        return deck;
    }

    public void clearBeforeRound(ArrayList<Player> players) {
        for (Player p : players) {
            p.getDraft().clear();
            p.getHand().clear();
            p.getNextPlayersHand().clear();
        }
    }

    /**
     * @param player Player whos hand wants printed
     */
    public void printCurrentHand(Player player) {
        printMessage(player, printOfCurrentHand(player));
    }

    /**
     * @param player Player whos hand wants printed
     * @return String String that holds a print of the current hand
     */
    public String printOfCurrentHand(Player player) {
        return "\n*****************************\nYour current hand: \n" + printCards(player.getHand());
    }

    /**
     * @param player  Player to send message to
     * @param message The message to send
     */
    public void printMessage(Player player, String message) {
        if (player.getPlayerCommunication() != null) {
            player.getPlayerCommunication().sendMessage(message);
        }
    }

    /**
     * @param cards An array of the cards that want to be printed
     * @return String The array/cards in the form of a string
     */
    public String printCards(ArrayList<Card> cards) {
        StringBuilder printString = new StringBuilder();

        for (Card card : cards) {
            printString.append(card.printCard(card)).append("\n"); // Call printCard and append the result
        }

        return printString.toString();
    }

    /**
     * @param players All the players of the game
     */
    public void printAfterRound(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getPlayerCommunication() != null) {
                player.getPlayerCommunication()
                        .sendMessage("********************************\nYour draft this round: \n"
                                + printCards(player.getDraft()) + "\n");
                player.getPlayerCommunication()
                        .sendMessage("The following regions have now been completed: " + finishedRegions);
            }
        }

    }

    /**
     * @param players Players from game to calculate winner
     * @return Player The winner of the game
     */
    public Player calculateWinner(ArrayList<Player> players) {
        Player highScore = players.get(0);

        for (Player player : players) {
            if (player.getFinalScore() > highScore.getFinalScore()) {
                highScore = player;
            } else if (player.getFinalScore() == highScore.getFinalScore()) {
                int currentPlayerThrowCatch = calculateThrowCatchScoreTie(player);
                int highScoreThrowCatch = calculateThrowCatchScoreTie(highScore);

                if (currentPlayerThrowCatch > highScoreThrowCatch) {
                    highScore = player;
                }
            }
        }

        return highScore;
    }

    /**
     * @param highScore Player who won
     * @return String String tha prints player who won
     */
    public String printOfWinner(Player highScore) {
        StringBuilder printString = new StringBuilder();
        printString.append("The winner is player: " + highScore.getPlayerID()
                + " with " + highScore.getFinalScore() + " points");
        return printString.toString();
    }

    /**
     * @param players Array of all players from game who should be used to find
     *                winner
     */
    public void checkWinner(ArrayList<Player> players) {
        Player highScore = calculateWinner(players);
        String winnerString = printOfWinner(highScore);
        for (Player p : players) {
            printMessage(p, winnerString);
        }
    }

    private int calculateThrowCatchScoreTie(Player player) {
        int totalT = 0;
        String t = "Throw and Catch score";
        totalT += player.getRScore().get(0).get(t) +
                player.getRScore().get(1).get(t) +
                player.getRScore().get(2).get(t) +
                player.getRScore().get(3).get(t);

        return totalT;
    }

    /**
     * @param players The players who's draft should be printed after each round
     */
    public void printAllPlayersDraft(ArrayList<Player> players) {
        for (int pID = 0; pID < players.size(); pID++) {
            Player currentPlayer = players.get(pID);
            String draftMessage = generateDraftMessage(players, pID);

            printMessage(currentPlayer, draftMessage);
        }
    }

    /**
     * @param players         All players in the game to print
     * @param currentPlayerID The id of the current player, who should not be
     *                        included in the print
     * @return String A string of what all players besides current player has
     *         drafted
     */
    public String generateDraftMessage(ArrayList<Player> players, int currentPlayerID) {
        StringBuilder message = new StringBuilder();
        for (int pID = 0; pID < players.size(); pID++) {
            Player player = players.get(pID);
            if (pID != currentPlayerID) {
                if (player.getDraft().size() > 1) {
                    message.append("Player ").append(pID).append(" has drafted\n")
                            .append(printCards(new ArrayList<>(player.getDraft().subList(1, player.getDraft().size()))))
                            .append("\n");
                } else {
                    message.append("Player ").append(pID).append(" has drafted a hidden Throw Card. \n");
                }

            }
        }

        return message.toString();
    }

    public IGameRules getGameRules() {
        return gameRules;
    }

    public IScoring getScoring() {
        return scoring;
    }

    public Card[] getCards() {
        return cards;
    }

    public String[] getRegions() {
        return regions;
    }

    public ArrayList<String> getFinishedRegions() {
        return finishedRegions;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
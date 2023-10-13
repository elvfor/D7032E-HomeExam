package game.logic;

import java.util.ArrayList;
import java.util.Arrays;

import card.Card;
import game.scoring.IScoring;
import player.HumanPlayer;
import player.Player;

public class GameLogic {
    private IGameRules gameRules;
    private IScoring scoring;
    private Card[] cards;
    private String[] regions;
    private ArrayList<String> finishedRegions = new ArrayList<String>();

    public GameLogic(IGameRules gameRules, IScoring scoring, Card[] cards, String[] regions){
        this.gameRules = gameRules;
        this.scoring = scoring;
        this.cards = cards;
        this.regions = regions;
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

    public void setCards(Card[] cards){
        this.cards = cards;
    }
        public void printCurrentDraft(Player player){
        if (player instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) player;
            if(player.getDraft().size() == 0){
                humanPlayer.getPlayerCommnication().sendMessage("You haven't drafted any cards yet");
            } else {
                humanPlayer.getPlayerCommnication().sendMessage("\n*****************************\nYour current draft: \n" + printCards(player.getDraft()));
            }
        }
    }
    public ArrayList<Card> resetRound(ArrayList<Player> players){
        ArrayList<Card> deck = new ArrayList<>(Arrays.asList(cards)); 
        clearBeforeRound(players);
        return deck;
    }
    private void clearBeforeRound(ArrayList<Player> players){
        for(Player p : players){
            p.getDraft().clear();
            p.getHand().clear();
            p.getNextPlayersHand().clear();
        }
    }

    public void printCurrentHand(Player player){
        if (player instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) player;
            humanPlayer.getPlayerCommnication().sendMessage("\n*****************************\nYour current hand: \n" + printCards(player.getHand()));
            
        }
    }

    private String printCards(ArrayList<Card> cards) {
        StringBuilder printString = new StringBuilder();
        
        for (Card card : cards) {
            printString.append(card.printCard(card)).append("\n"); // Call printCard and append the result
        }
    
        return printString.toString();
    }
    public void printAfterRound(Player player){
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("********************************\nYour draft this round: \n"+ printCards(player.getDraft())+"\n");
            humanPlayer.getPlayerCommnication().sendMessage("The following regions have now been completed: "+ finishedRegions);

        }
    }
    public void checkWinner(ArrayList<Player> players){
        Player highScore = players.get(0);
        int highScoreThrowCatch = calculateThrowCatchScore(players.get(0));

        for (Player player : players) {
            int currentPlayerThrowCatch = calculateThrowCatchScore(player);
            
            if (player.getFinalScore() > highScore.getFinalScore() || (player.getFinalScore() == highScore.getFinalScore() && currentPlayerThrowCatch > highScoreThrowCatch)) {
                highScore = player;
                highScoreThrowCatch = currentPlayerThrowCatch;
            }
        }

        for (Player player : players) {
            if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("The winner is player: " + players.indexOf(highScore) + " with " + highScore.getFinalScore() + " points");
            }
        }
    }


// Add a method to calculate the Throw & Catch score for a player
    private int calculateThrowCatchScore(Player player) {
        int totalT = 0;
        String t="Throw and Catch score";
        totalT += player.getRScore().get(0).get(t) +
            player.getRScore().get(1).get(t) +
            player.getRScore().get(2).get(t) +
            player.getRScore().get(3).get(t);

        return totalT;
    }


    public void printAllPlayersDraft(ArrayList<Player> players){
        for(int pID=0; pID<players.size(); pID++) {
            for(Player sendToPlayer : players) {
                Player id=players.get(pID);
                if (sendToPlayer instanceof HumanPlayer) {
                    HumanPlayer humanPlayer = (HumanPlayer) sendToPlayer;
                    humanPlayer.getPlayerCommnication().sendMessage("\nPlayer " + pID + " has drafted\n" + printCards(new ArrayList<>(id.getDraft().subList(1, id.getDraft().size()))));
                }
            }
        }
    }
}
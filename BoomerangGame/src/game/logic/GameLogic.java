package game.logic;

import java.util.ArrayList;

import card.Card;
import game.scoring.IScoring;
import player.HumanPlayer;
import player.Player;

public class GameLogic {
    private IGameRules gameRules;
    private IScoring scoring;
    private ArrayList<Card> cards;
    private String[] regions;
    private ArrayList<String> finishedRegions;

    public GameLogic(IGameRules gameRules, IScoring scoring, ArrayList<Card> cards){
        this.gameRules = gameRules;
        this.scoring = scoring;
        this.cards = cards;
    }

    public IGameRules getGameRules() {
        return gameRules;
    }

    public IScoring getScoring() {
        return scoring;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String[] getRegions() {
        return regions;
    }

    public ArrayList<String> getFinishedRegions() {
        return finishedRegions;
    }

    public void setCards(ArrayList<Card> cards){
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

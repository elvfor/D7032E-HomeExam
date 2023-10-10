package game.gameContext;

import java.util.ArrayList;
import java.util.List;

import card.AustralianCard;
import card.Card;
import game.logic.IGameLogic;
import game.scoring.IScoring;
import game.state.IGameState;
import player.HumanPlayer;
import player.Player;

public class GameContext {
    private IGameLogic logic;
    private IScoring scoring;
    private ArrayList<Player> players;
    private ArrayList<Card> cards;
    private IGameState currentState;
    private Player activePlayer;

    /*public void GameContext(List<Player> players, List<Card> cards, GameState currentState, Player activePlayer){
        this.players = players;
        this.cards = cards;
        this.currentState = currentState;
        this.activePlayer = activePlayer;
    }*/

    public void setCurrentState(IGameState state){
        this.currentState = state;
    }

    public IGameState getCurrentState(){
        return currentState;
    } 

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }
    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

    public void setRules(IGameLogic logic){
        this.logic = logic;
    }

    public void setScoring(IScoring scoring){
        this.scoring = scoring;
    }

        public ArrayList<Card> getCards(){
        return cards;
    } 

    public IGameLogic getGameLogic(){
        return logic;
    } 

    public IScoring getScoring(){
        return scoring;
    } 
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void printCurrentDraft(Player player){
        if (player instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            if(player.getDraft().size() == 0){
                humanPlayer.getPlayerCommnication().sendMessage("You haven't drafted any cards yet");
            } else {
                humanPlayer.getPlayerCommnication().sendMessage("\n*****************************\nYour current draft: \n" + printCards(player.getDraft()));
            }
        }
    }
    public void printCurrentHand(Player player){
        printCards(player.getHand());
    }
    private String printCards(ArrayList<Card> cards) {
        StringBuilder printString = new StringBuilder();
        
        for (Card card : cards) {
            printString.append(card.printCard(card)).append("\n"); // Call printCard and append the result
        }
    
        return printString.toString();
    }
    public void printTest(Player player){
        // String.format("%-20s", str); % = format sequence. - = string is left-justified (no - = right-justified). 20 = string will be 20 long. s = character string format code		
			String printString = String.format("%27s", "Site [letter] (nr):  ");
			for(Card c : cards) { //print name letter and number of each card
				printString += String.format("%-35s", c.getName()+ " ["+c.getLetter()+"] ("+c.getNumber()+")");
			}
			printString += "\n" + String.format("%27s", "Region:  ");
			for(Card c : cards) { //print name letter and number of each card
				printString += String.format("%-35s", c.getRegion());
			}			
			printString +="\n" + String.format("%27s", "Collections:  ");
			for(Card c : cards) { //print collections of each card, separate with tab
                if (c instanceof AustralianCard) {
                    AustralianCard acard = (AustralianCard) c; // Cast to Australian card
				printString  += String.format("%-35s", acard.getCollection());
                }
			}
			printString +="\n" + String.format("%27s", "Animals:  ");
			for(Card c : cards) { //print animals of each card, separate with tab
                if (c instanceof AustralianCard) {
                    AustralianCard acard = (AustralianCard) c; // Cast to Australian card
				printString += String.format("%-35s", acard.getAnimal());
                }
			}
			printString +="\n" + String.format("%27s", "Activities:  ");
			for(Card c : cards) { //print activities of each card, separate with tab
                if (c instanceof AustralianCard) {
                AustralianCard acard = (AustralianCard) c; // Cast to Australian card
				printString += String.format("%-35s", acard.getActivity());
                }
			}
			System.out.println(printString);

    }
    
}

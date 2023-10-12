package player.actions;

import java.util.Random;

import card.Card;
import player.Player;

public class BotPlayerActionsStandard implements IPlayerActions{

    @Override
    public void pickThrowCard(Player player) {
        String response = "";
        Random rnd = new Random();
		response = player.getHand().get(rnd.nextInt(player.getHand().size()-1)).getLetter();
			for(Card c : player.getHand()) {
				if(c.getLetter().equalsIgnoreCase(response)) {
                    player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                    //player.getNextPlayersHand().addAll(player.getHand());
                    break;
				}
			}
            player.getNextPlayersHand().addAll(player.getHand());
            player.getHand().clear();
    }

    @Override
    public void pickCardFromDraft(Player player) {
        String response = "";
        Random rnd = new Random();
		response = player.getHand().get(rnd.nextInt(player.getHand().size()-1)).getLetter();
			for(Card c : player.getHand()) {
				if(c.getLetter().equalsIgnoreCase(response)) {
					player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                    break;
				}
			}
    }
    
}

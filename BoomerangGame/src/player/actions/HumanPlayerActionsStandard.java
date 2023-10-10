package player.actions;

import java.util.Random;

import card.Card;
import player.HumanPlayer;
import player.Player;

public class HumanPlayerActionsStandard implements IPlayerActions{
    
    @Override
    public void pickThrowCard(Player player) {// Cast to HumanPlayer
        HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
        humanPlayer.getPlayerCommnication().sendMessage("Type the letter of the card to draft as Throw card. ");
			String response = "";
			response = humanPlayer.getPlayerCommnication().receiveInput();

			for(Card c : player.getHand()) {
				if(c.getLetter().equalsIgnoreCase(response)) {
					player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                    break;
				}
			}
    }

    @Override
    public void pickCardFromDraft(Player player) {
       
    }
}
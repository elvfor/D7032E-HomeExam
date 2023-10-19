package ltu.player.actions;

import java.util.Random;

import ltu.card.Card;
import ltu.player.Player;

/**
 * This class represents the logic a bot player has during the game. Implements
 * the IPlayerActions interface and has logic for how the bot picks a card from
 * its hand
 */
public class BotPlayerActionsStandard implements IPlayerActions {

    /**
     * @param player The bot player who excetue the pick card action
     */
    @Override
    public void pickCardFromHand(Player player) {
        String response = "";
        Random rnd = new Random();
        response = player.getHand().get(rnd.nextInt(player.getHand().size() - 1)).getLetter();
        for (Card c : player.getHand()) {
            if (c.getLetter().equalsIgnoreCase(response)) {
                player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                break;
            }
        }
        player.getNextPlayersHand().addAll(player.getHand());
        player.getHand().clear();
    }

}

package ltu.player.actions;

import ltu.card.Card;
import ltu.player.Player;

public class HumanPlayerActionsStandard implements IPlayerActions {

    /*
     * @Override
     * public void pickThrowCard(Player player) {
     * player.getPlayerCommunication().
     * sendMessage("Type the letter of the card to draft as Throw card. ");
     * String response = "";
     * boolean validChoice = false;
     * 
     * while (!validChoice) {
     * response = player.getPlayerCommunication().receiveInput();
     * 
     * for (Card c : player.getHand()) {
     * if (c.getLetter().equalsIgnoreCase(response)) {
     * player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
     * validChoice = true;
     * break;
     * }
     * }
     * 
     * if (!validChoice) {
     * // The chosen letter is not in the player's hand, so prompt for a new card.
     * player.getPlayerCommunication().
     * sendMessage("Invalid choice. Please choose a card from your hand.");
     * }
     * }
     * player.getNextPlayersHand().addAll(player.getHand());
     * player.getHand().clear();
     * 
     * }
     */

    @Override
    public void pickCardFromHand(Player player) {
        player.getPlayerCommunication().sendMessage("Type the letter of the card to draft. ");
        String response = "";
        boolean validChoice = false;

        while (!validChoice) {
            response = player.getPlayerCommunication().receiveInput();

            for (Card c : player.getHand()) {
                if (c.getLetter().equalsIgnoreCase(response)) {
                    player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                    validChoice = true;
                    break;
                }
            }

            if (!validChoice) {
                player.getPlayerCommunication().sendMessage("Invalid choice. Please choose a card from your hand.");
            }
        }
        player.getNextPlayersHand().addAll(player.getHand());
        player.getHand().clear();

    }

}
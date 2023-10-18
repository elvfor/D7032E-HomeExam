package ltu.player.actions;

import ltu.card.Card;
import ltu.exception.InvalidCardSelectionException;
import ltu.player.Player;

public class HumanPlayerActionsStandard implements IPlayerActions {

    @Override
    public void pickCardFromHand(Player player) {
        player.getPlayerCommunication().sendMessage("Type the letter of the card to draft. ");
        String response = "";

        while (true) {
            try {
                response = player.getPlayerCommunication().receiveInput();
                boolean validChoice = false;

                for (Card c : player.getHand()) {
                    if (c.getLetter().equalsIgnoreCase(response)) {
                        player.getDraft().add(player.getHand().remove(player.getHand().indexOf(c)));
                        validChoice = true;
                        break;
                    }
                }

                if (!validChoice) {
                    throw new InvalidCardSelectionException("Invalid choice. Please choose a card from your hand.");
                }

                player.getNextPlayersHand().addAll(player.getHand());
                player.getHand().clear();

                break; // Exit the loop after a valid choice
            } catch (InvalidCardSelectionException e) {
                player.getPlayerCommunication().sendMessage(e.getMessage());
            }
        }
    }

}
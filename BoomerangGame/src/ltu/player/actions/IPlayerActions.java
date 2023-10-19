package ltu.player.actions;

import ltu.player.Player;

/**
 * This interface represents the actioon of how different players can execute
 * the pick card action during the game. Each player is injected with a class
 * that implements this interface
 */
public interface IPlayerActions {

    void pickCardFromHand(Player player);
}
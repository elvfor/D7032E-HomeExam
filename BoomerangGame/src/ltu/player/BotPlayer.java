package ltu.player;

import ltu.player.Player;

import ltu.player.actions.IPlayerActions;

/**
 * This class represents the bot players that play the game, extends the
 * Player class and has currently no IPlayerCommunication since this type of bot
 * does not need to communicate with the game
 */
public class BotPlayer extends Player {

    public BotPlayer(int playerID, IPlayerActions playerActions) {
        super(playerID, playerActions);

    }

}

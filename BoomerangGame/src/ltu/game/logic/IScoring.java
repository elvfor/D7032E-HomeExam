package ltu.game.logic;

import ltu.player.Player;

/**
 * This interface is used when creating the certain scoring mechanisms. Contains
 * functions that all scorings should have
 */
public interface IScoring {
    void roundScore(Player player, GameLogic gameLogic);

    void totalScore(Player player, GameLogic gameLogic);

    int additionalScore(Player player, GameLogic gameLogic);
}

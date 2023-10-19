package ltu.game.scoring;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

/**
 * This interface sets up the generic functions for calculating the score of the
 * game. All game modes scoring should implement this interface
 */
public interface IScoring {
    void roundScore(Player player, GameLogic gameLogic);

    void totalScore(Player player, GameLogic gameLogic);

    int additionalScore(Player player, GameLogic gameLogic);
}
package ltu.game.scoring;

import ltu.game.logic.GameLogic;
import ltu.player.Player;

public interface IScoring {
    void roundScore(Player player, GameLogic gameLogic);

    void totalScore(Player player, GameLogic gameLogic);

    int additionalScore(Player player, GameLogic gameLogic);
}

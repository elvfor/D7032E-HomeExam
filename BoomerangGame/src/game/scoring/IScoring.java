package game.scoring;

import game.logic.GameLogic;
import game.state.GameContext;
import player.Player;

public interface IScoring {
    void roundScore(Player player, GameLogic gameLogic);
    void totalScore(Player player, GameLogic gameLogic);
    int additionalScore(Player player, GameLogic gameLogic);
}

package player.actions;

import player.Player;

public interface IPlayerActions{
    void pickThrowCard(Player player);
    void pickCardFromDraft(Player player);
    void pickScoringCard(Player player);
}
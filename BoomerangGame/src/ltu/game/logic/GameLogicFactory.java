package ltu.game.logic;

import ltu.game.logic.IGameRules;
import ltu.game.scoring.IScoring;
import ltu.game.logic.standardGameRules;
import ltu.game.scoring.australiaScoring;

/**
 * This class is used for abstracting the creating of rules and scoring
 * mechanisms
 */
public class GameLogicFactory {
    public static IScoring createScoring(String version) {
        switch (version) {
            case "Australia":
                return new australiaScoring();
            // case "European":
            // return new EuropeanGameRules();
            // Add more cases for other game versions
            default:
                throw new IllegalArgumentException("Unsupported game version: " + version);
        }
    }

    public static IGameRules createGameRules(String type) {
        switch (type) {
            case "Standard":
                return new standardGameRules();
            default:
                throw new IllegalArgumentException("Unsupported rule type: " + type);
        }
    }
}
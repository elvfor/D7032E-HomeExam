package game.logic;

import game.scoring.IScoring;
import game.scoring.australiaScoring;

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
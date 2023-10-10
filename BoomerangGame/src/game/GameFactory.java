package game;

import game.rules.IRules;
import game.rules.standardRules;
import game.scoring.IScoring;
import game.scoring.australiaScoring;

public class GameFactory {
    public static IScoring createScoring(String version) {
        switch (version) {
            case "Australia":
                return new australiaScoring();
            //case "European":
            //    return new EuropeanGameRules();
            // Add more cases for other game versions
            default:
                throw new IllegalArgumentException("Unsupported game version: " + version);
        }
    }

    public static IRules createGameRules(String type) {
        switch(type){
            case "Standard":
                return new standardRules();
            default:
             throw new IllegalArgumentException("Unsupported rule type: " + type);
        }
    }
}
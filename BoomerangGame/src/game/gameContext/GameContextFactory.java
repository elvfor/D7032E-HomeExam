package game.gameContext;

import game.logic.IGameLogic;
import game.logic.standardGameLogic;
import game.scoring.IScoring;
import game.scoring.australiaScoring;

public class GameContextFactory {
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

    public static IGameLogic createGameRules(String type) {
        switch(type){
            case "Standard":
                return new standardGameLogic();
            default:
             throw new IllegalArgumentException("Unsupported rule type: " + type);
        }
    }

}
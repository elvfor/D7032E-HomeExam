package game.scoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import card.AustralianCard;
import card.Card;
import game.logic.GameLogic;
import player.HumanPlayer;
import player.Player;

public class australiaScoring implements IScoring{

    @Override
    public void roundScore(Player player, GameLogic gameLogic) {
        HashMap<String, Integer> score = new HashMap<String, Integer>();
        score.put("Throw and Catch score", throwCatchScore(player));
        score.put("Tourist sites score", regionScore(player, gameLogic));
        score.put("Reion complete score", regionCompleteScore(player, gameLogic));
        score.put("Collections score", collectionScore(player, gameLogic));
        score.put("Animals score", animalScore(player, gameLogic));
        ArrayList<HashMap<String, Integer>> rScore = player.getRScore();
        //activityScore(player, gameLogic);
        score.put("Sctivity Score", additionalScore(player, gameLogic));
        rScore.add(score);
        player.setRScore(rScore);
    }

    @Override
    public void totalScore(Player player, GameLogic gameLogic) {
        String t="Throw and Catch score", to="Tourist sites score", c="Collections score", a="Animals score";
        String[] act={"Indigenous Culture","Bushwalking","Swimming","Sightseeing"};

				int totalAct = 0;
				for(String anAct : act) {
					if(player.getActivitiesScore().get(anAct)==null)
						player.addToActivitiesScore(anAct, 0);
					totalAct+=player.getActivitiesScore().get(anAct).intValue();
				}
				int totalT=player.getRScore().get(0).get(t).intValue()+player.getRScore().get(1).get(t).intValue()+player.getRScore().get(2).get(t).intValue()+player.getRScore().get(3).get(t).intValue();
				int totalTo=player.getRScore().get(0).get(to).intValue()+player.getRScore().get(1).get(to).intValue()+player.getRScore().get(2).get(to).intValue()+player.getRScore().get(3).get(to).intValue();
				int totalC=player.getRScore().get(0).get(c).intValue()+player.getRScore().get(1).get(c).intValue()+player.getRScore().get(2).get(c).intValue()+player.getRScore().get(3).get(c).intValue();
				int totalA=player.getRScore().get(0).get(a).intValue()+player.getRScore().get(1).get(a).intValue()+player.getRScore().get(2).get(a).intValue()+player.getRScore().get(3).get(a).intValue();
				//Requirement 12
					String finalScore =  "                       Round 1\tRound 2\tRound 3\tRound 4\tTotal\n";
					       finalScore += "Throw and Catch score:   " + player.getRScore().get(0).get(t)+"\t  "+player.getRScore().get(1).get(t)+"\t  "+player.getRScore().get(2).get(t)+"\t  "+player.getRScore().get(3).get(t)+"\t  "+totalT+"\n";
					       finalScore += "  Tourist sites score:   " + player.getRScore().get(0).get(to)+"\t  "+player.getRScore().get(1).get(to)+"\t  "+player.getRScore().get(2).get(to)+"\t  "+player.getRScore().get(3).get(to)+"\t  "+totalTo+"\n";;
					       finalScore += "    Collections score:   " + player.getRScore().get(0).get(c)+"\t  "+player.getRScore().get(1).get(c)+"\t  "+player.getRScore().get(2).get(c)+"\t  "+player.getRScore().get(3).get(c)+"\t  "+totalC+"\n";
						   finalScore += "        Animals score:   " + player.getRScore().get(0).get(a)+"\t  "+player.getRScore().get(1).get(a)+"\t  "+player.getRScore().get(2).get(a)+"\t  "+player.getRScore().get(3).get(a)+"\t  "+totalA+"\n";	
	  				       finalScore += "                        IndC\tBushw\tSwim\tSights\n";
	  				       finalScore += "     Activities score:   " + player.getActivitiesScore().get(act[0])+"\t  "+ player.getActivitiesScore().get(act[1])+"\t  "+player.getActivitiesScore().get(act[2])+"\t  "+player.getActivitiesScore().get(act[3])+"\t  "+totalAct+"\n";
	  				       finalScore += "       Region bonuses:   " + (player.getRegion().size()*3)+"\n";
	  				       finalScore += "          Total score:   " + (totalT+totalTo+totalC+totalA+totalAct+(player.getRegion().size()*3)) + " points\n";
	  				if(player instanceof HumanPlayer){
                HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
                humanPlayer.getPlayerCommnication().sendMessage("\n*********************************************\n"+finalScore);
                    }
                     //(totalT+totalC+totalA+totalAct+(region.size()*3));
    }

    @Override
    public int additionalScore(Player player, GameLogic gameLogic) {
        //Requirement 10e Calculate score for the Activities if the player wants to score it
		String[] act={"Indigenous Culture","Bushwalking","Swimming","Sightseeing"};
		if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("This round you have gathered the following new activities:");
        }
		HashMap<String, Integer> newActivitiesMap = new HashMap<>();
		String newActivities = "";
		for(String thisAct : act) { //Requirement 10e(ii)
			if(!player.getActivitiesScore().containsKey(thisAct)) {
				int frequency = numberThings(thisAct, "Activities", player);
				newActivities += thisAct+"(# "+frequency+")\t";
				newActivitiesMap.put(thisAct, frequency);
			}
		}

        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage(newActivities + "\nSelect if you wish to score one of them");
            int countRoundActivities = 0;
			int[] scoreTable = {0,2,4,7,10,15};
			for(Map.Entry<String, Integer> entry : newActivitiesMap.entrySet()) {
				int frequency = numberThings(entry.getKey(), "Activities", player);
				int scoret = (frequency>0)?scoreTable[frequency-1]:0;
				humanPlayer.getPlayerCommnication().sendMessage("Want to keep " + entry.getKey() + "("+entry.getValue()+") [" + scoret + " points]? (Y/N)");
				String response = "";

				response = humanPlayer.getPlayerCommnication().receiveInput();
				if(response.equalsIgnoreCase("Y")) {
					player.getActivitiesScore().put(entry.getKey(), scoret);
					humanPlayer.getPlayerCommnication().sendMessage("This round you scored this activity: " + entry.getKey() + "["+scoret + " points]");
					//We do not need to add the Activity score to the score variable, since it's stored separately in the activitiesScore hashmap
					countRoundActivities = scoret;
					break; //Requirement 10e(i) exit the loop since you are only allowed to score one activity per round
				}
			}
            player.setFinalScore(player.getFinalScore()+countRoundActivities);
            return countRoundActivities;
        }else{
            int countRoundActivities = 0;
			int[] scoreTable = {0,2,4,7,10,15};
			for(Map.Entry<String, Integer> entry : newActivitiesMap.entrySet()) {
				int frequency = numberThings(entry.getKey(), "Activities", player);
				int scoret = (frequency>0)?scoreTable[frequency-1]:0;
				String response = "";
					response = "Y";
				if(response.equalsIgnoreCase("Y")) {
					player.getActivitiesScore().put(entry.getKey(), scoret);
					countRoundActivities = scoret;
					break; //Requirement 10e(i) exit the loop since you are only allowed to score one activity per round
				}
			}
            player.setFinalScore(player.getFinalScore()+countRoundActivities);
            return countRoundActivities;
        }
    }
    private int throwCatchScore(Player player){
        //Requirement 10a
		int throwCatchScore = Math.abs(player.getDraft().get(0).getNumber() - player.getDraft().get(6).getNumber());
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("This round you scored " + throwCatchScore + " as your Throw and catch score");
        }
        player.setFinalScore(player.getFinalScore()+throwCatchScore);
        return throwCatchScore;
    }

    private int regionScore(Player player, GameLogic gameLogic){
        //Requirement 10b
			int thisRoundSites = 0;			
			for(Card draftCard : player.getDraft()) {
				if(!player.getSites().containsKey(draftCard.getLetter())) {
					thisRoundSites++;
					player.getSites().put(draftCard.getLetter(), draftCard.getRegion());
				}
            
		}int regionRoundScore = regionCompleteScore(player, gameLogic);
        if(player instanceof HumanPlayer){
                HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
                humanPlayer.getPlayerCommnication().sendMessage("This round you scored " + thisRoundSites + " new sites points and " + regionRoundScore + " points for completing regions");
            }
        player.setFinalScore(player.getFinalScore()+thisRoundSites);
        return thisRoundSites;
    }
    private int regionCompleteScore(Player player, GameLogic gameLogic){
        //Requirement 10b(i+ii)
        int tempScore = 0;
		for(int r=0; r<gameLogic.getRegions().length; r++) {
			boolean regionComplete = false;
				if(!gameLogic.getFinishedRegions().contains(gameLogic.getRegions()[r]) && (checkRegionComplete(gameLogic.getRegions()[r],player))) {
					player.getRegion().add(gameLogic.getRegions()[r]);
					int prevRoundScore = player.getRegionRoundScore();
                    player.setRegionRoundScore(prevRoundScore+=3);
					regionComplete = true;
                    tempScore+=3;
				}
                if(regionComplete) {
                    gameLogic.getFinishedRegions().add(gameLogic.getRegions()[r]); //Requirement 10b(ii)
                }
            }
            player.setFinalScore(player.getFinalScore()+tempScore);
            return tempScore;
		}
    private boolean checkRegionComplete(String theRegion, Player player) {
			ArrayList<String> temp = new ArrayList<String>();
			for(Map.Entry<String,String> set : player.getSites().entrySet()) {
				if(set.getValue().equals(theRegion))
					temp.add(set.getKey());				
			}
			for(Card c : player.getDraft()) {
				if(c.getRegion().equals(theRegion) && !temp.contains(c.getLetter())) {
					temp.add(c.getLetter());
				}
			}
			if(temp.size() == 4)
				return true;
			else
				return false;
		}
    private int collectionScore(Player player, GameLogic gameLogic) {
        //Requirement 10c - Calculate score for Collections
        Map<String, Integer> collectionValues = new HashMap<>();
        collectionValues.put("Leaves", 1);
        collectionValues.put("Wildflowers", 2);
        collectionValues.put("Shells", 3);
        collectionValues.put("Souvenirs", 5);
        
        String thisRoundCollections = "";
        int countRoundCollections = 0;
        String[] collectionarr = {"Leaves", "Wildflowers", "Shells", "Souvenirs"};
        
        for (String thisColl : collectionarr) {
            int nr = numberThings(thisColl, "Collections", player);
            int sumColl = collectionValues.getOrDefault(thisColl, 0) * nr;
            thisRoundCollections += thisColl + " [" + sumColl + " points]\t";
            countRoundCollections += sumColl;
        }
        
        int totalCollectionScore = (countRoundCollections <= 7) ? countRoundCollections * 2 : countRoundCollections;
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("This round you scored these collections: " + thisRoundCollections);
        }
        player.setFinalScore(player.getFinalScore()+totalCollectionScore);
        return totalCollectionScore;
    }
    private int animalScore(Player player, GameLogic gameLogic) {
        // Requirement 10d Calculate score for Animals
        Map<String, Integer> animalValues = new HashMap<>();
        animalValues.put("Kangaroos", 3);
        animalValues.put("Emus", 4);
        animalValues.put("Wombats", 5);
        animalValues.put("Koalas", 7);
        animalValues.put("Platypuses", 9);
    
        String thisRoundAnimals = "";
        int countRoundAnimals = 0;
        String[] animalarr = {"Kangaroos", "Emus", "Wombats", "Koalas", "Platypuses"};
    
        for (String thisAnim : animalarr) {
            int frequency = numberThings(thisAnim, "Animals", player);
            if (frequency >= 2) {
                int animalValue = animalValues.getOrDefault(thisAnim, 0);
                int animalPoints = animalValue * (frequency / 2);
                thisRoundAnimals += thisAnim + " [" + animalPoints + " points]\t";
                countRoundAnimals += animalPoints;
            }
        }
    
        if (player instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) player;
            humanPlayer.getPlayerCommnication().sendMessage("This round you scored these Animals: " + thisRoundAnimals);
        }
        player.setFinalScore(player.getFinalScore()+countRoundAnimals);
        return countRoundAnimals;
    }
    private int numberThings(String aThing, String category, Player player) {
        int nrThings = 0;
        
        for(Card Card : player.getDraft()) {
            AustralianCard aCard = (AustralianCard) Card;
            if(category.equals("Collections")) {
                if(aCard.getCollection().equals(aThing)) {nrThings++;}
            } else if(category.equals("Animals")) {
                if(aCard.getAnimal().equals(aThing)) {nrThings++;}
            } else if(category.equals("Activities")) {
                if(aCard.getActivity().equals(aThing)) {nrThings++;}
            }
        }
        return nrThings;
    }

    private String activityScore(Player player, GameLogic gameLogic){
        //Requirement 10e Calculate score for the Activities if the player wants to score it
		String[] act={"Indigenous Culture","Bushwalking","Swimming","Sightseeing"};
		if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("This round you have gathered the following new activities:");
        }
		HashMap<String, Integer> newActivitiesMap = new HashMap<>();
		String newActivities = "";
		for(String thisAct : act) { //Requirement 10e(ii)
			if(!player.getActivitiesScore().containsKey(thisAct)) {
				int frequency = numberThings(thisAct, "Activities", player);
				newActivities += thisAct+"(# "+frequency+")\t";
				newActivitiesMap.put(thisAct, frequency);
			}
		}
        return newActivities;
    }
}
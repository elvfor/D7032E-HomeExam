package game.scoring;

import java.util.HashMap;
import java.util.Map;

import card.Card;
import player.HumanPlayer;
import player.Player;

public class australiaScoring implements IScoring{

    @Override
    public void roundScore(Player player) {
        HashMap<String, Integer> score = new HashMap<String, Integer>();
        score.put("Throw and Catch score", throwCatchScore(player));

        System.out.println("Calculate score");

    }

    @Override
    public void totalScore(Player player) {
        System.out.println("Calculate total score");
    }
    
    private int throwCatchScore(Player player){
        //Requirement 10a
		int throwCatchScore = Math.abs(player.getDraft().get(0).getNumber() - player.getDraft().get(6).getNumber());
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player; // Cast to HumanPlayer
            humanPlayer.getPlayerCommnication().sendMessage("This round you scored " + throwCatchScore + " as your Throw and catch score");
        }
        return throwCatchScore;
    }

    private int regionScore(Player player){
        //Requirement 10b
			int thisRoundSites = 0;			
			for(Card draftCard : player.getDraft()) {
				if(!player.getSites().containsKey(draftCard.getLetter())) {
					thisRoundSites++;
					player.getSites().put(draftCard.getLetter(), draftCard.getRegion());
				}

            //sendMessage("This round you scored " + thisRoundSites + " new sites points and " + regionRoundScore + " points for completing regions");
            //score.put("Tourist sites score", thisRoundSites);
			}
            return 0;
        }

    }
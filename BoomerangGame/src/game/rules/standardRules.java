package game.rules;

public class standardRules implements IRules{

    @Override
    public void passCards() {
        System.out.println("passing cards");
    }

    @Override
    public void cardDraft() {
        System.out.println("Drafting cards");

    }
    
}

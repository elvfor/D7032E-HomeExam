package card;

import java.util.ArrayList;

public class AustralianCard extends Card {
    private String Collection, Animal, Activity;

    public AustralianCard(String name, String letter, String region, int number, String Collection, String Animal,
            String Activity) {
        super(name, letter, region, number);
        this.Collection = Collection;
        this.Animal = Animal;
        this.Activity = Activity;
    }

    public String getCollection() {
        return Collection;
    }

    public String getAnimal() {
        return Animal;
    }

    public String getActivity() {
        return Activity;
    }

    @Override
    public String printCards(ArrayList<Card> cards) {
        // String.format("%-20s", str); % = format sequence. - = string is
        // left-justified (no - = right-justified). 20 = string will be 20 long. s =
        // character string format code
        String printString = String.format("%27s", "Site [letter] (nr):  ");
        for (Card c : cards) { // print name letter and number of each card
            printString += String.format("%-35s", c.getName() + " [" + c.getLetter() + "] (" + c.getNumber() + ")");
        }
        printString += "\n" + String.format("%27s", "Region:  ");
        for (Card c : cards) { // print name letter and number of each card
            printString += String.format("%-35s", c.getRegion());
        }
        printString += "\n" + String.format("%27s", "Collections:  ");
        for (Card c : cards) { // print collections of each card, separate with tab
            if (c instanceof AustralianCard) {
                AustralianCard acard = (AustralianCard) c; // Cast to Australian card
                printString += String.format("%-35s", acard.getCollection());
            }
        }
        printString += "\n" + String.format("%27s", "Animals:  ");
        for (Card c : cards) { // print animals of each card, separate with tab
            if (c instanceof AustralianCard) {
                AustralianCard acard = (AustralianCard) c; // Cast to Australian card
                printString += String.format("%-35s", acard.getAnimal());
            }
        }
        printString += "\n" + String.format("%27s", "Activities:  ");
        for (Card c : cards) { // print activities of each card, separate with tab
            if (c instanceof AustralianCard) {
                AustralianCard acard = (AustralianCard) c; // Cast to Australian card
                printString += String.format("%-35s", acard.getActivity());
            }
        }
        return printString;
    }

    @Override
    public String printCard(Card card) {
        String printString = String.format("%27s", "Site [letter] (nr): ");
        printString += String.format("%-35s",
                card.getName() + " [" + card.getLetter() + "] (" + card.getNumber() + ")");
        printString += "\n" + String.format("%27s", "Region: ");
        printString += String.format("%-35s", card.getRegion());

        if (card instanceof AustralianCard) {
            AustralianCard acard = (AustralianCard) card; // Cast to Australian card
            printString += "\n" + String.format("%27s", "Collections: ");
            printString += String.format("%-35s", acard.getCollection());
            printString += "\n" + String.format("%27s", "Animals: ");
            printString += String.format("%-35s", acard.getAnimal());
            printString += "\n" + String.format("%27s", "Activities: ");
            printString += String.format("%-35s", acard.getActivity());
            printString += "\n";
        }

        return printString;
    }

}
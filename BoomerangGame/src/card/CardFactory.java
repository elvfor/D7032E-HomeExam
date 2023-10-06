package card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import card.AustralianCard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.google.gson.Gson;
public class CardFactory {
    private List<Card> cards = new ArrayList<>();
    public List<Card> createCardFromConfig(JsonArray cardsData, String version) {

        for (int i = 0; i < cardsData.size(); i++) {
            JsonObject cardData = cardsData.get(i).getAsJsonObject();
            // Extract card properties from the JSON object
            String name = cardData.get("name").getAsString();
            String letter = cardData.get("letter").getAsString();
            String region = cardData.get("region").getAsString();
            int number = cardData.get("number").getAsInt();

            // Create a card based on the version
            if ("Australian".equals(version)) {
                String collections = cardData.get("collections").getAsString();
                String animals = cardData.get("animals").getAsString();
                String activities = cardData.get("activities").getAsString();
                cards.add(new AustralianCard(name, letter, region, number, collections, animals, activities));
            } else if ("European".equals(version)) {
                // Create a European card (if applicable) and add it to the list
            } else {
                // Handle other versions or throw an exception for unsupported versions
                throw new IllegalArgumentException("Unsupported card version: " + version);
            }
        }

        return cards;
    }
}

package ltu.card;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AustralianCardFactory implements ICardFactory {

    private Card[] arrayDeck;
    private String[] regions;

    /**
     * @return Card[] Used by factory to create cards from JSON file, returns the
     *         array of created cards
     * @throws IOException if something goes wrong with creating the cards
     */
    @Override
    public Card[] createCards() throws IOException {
        Card[] arrayDeck;
        JsonArray cardsData = createJSONCardsFromConfig("../config/australianCards.JSON");
        arrayDeck = new Card[cardsData.size()];
        for (int i = 0; i < cardsData.size(); i++) {
            JsonObject cardData = cardsData.get(i).getAsJsonObject();
            // Extract card properties from the JSON object
            String name = cardData.get("name").getAsString();
            String letter = cardData.get("letter").getAsString();
            String region = cardData.get("region").getAsString();
            int number = cardData.get("number").getAsInt();
            String collection = cardData.get("collection").getAsString();
            String animal = cardData.get("animal").getAsString();
            String activity = cardData.get("activity").getAsString();
            arrayDeck[i] = new AustralianCard(name, letter, region, number, collection, animal, activity);
        }
        return arrayDeck;
    }

    /**
     * @return String[] The array of regions that are created from a certain JSON
     *         file
     * @throws IOException if something goes wrong when creating the regions
     */
    public String[] createRegions() throws IOException {
        // Create a card based on the version

        JsonArray regionsJsonArray = createRegionFromConfig("../config/australianCards.JSON");
        Gson gson = new Gson();
        regions = gson.fromJson(regionsJsonArray, String[].class);
        return regions;
    }

    private static JsonArray createJSONCardsFromConfig(String configFilePath) throws IOException {
        FileReader fileReader = new FileReader(configFilePath);
        JsonObject config = JsonParser.parseReader(fileReader).getAsJsonObject();
        fileReader.close();

        // Extract the version from the JSON
        JsonArray cardsData = (JsonArray) config.get("cards");
        return cardsData;
    }

    private static JsonArray createRegionFromConfig(String configFilePath) throws IOException {
        FileReader fileReader = new FileReader(configFilePath);
        JsonObject config = JsonParser.parseReader(fileReader).getAsJsonObject();
        fileReader.close();

        // Extract the version from the JSON
        JsonArray cardsData = (JsonArray) config.get("regions");
        return cardsData;
    }
}

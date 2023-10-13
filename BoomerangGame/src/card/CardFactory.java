package card;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import card.AustralianCard;
import main.BoomerangGame;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.google.gson.Gson;
public class CardFactory {
    private Card[] arrayDeck;
    private String[] regions;
    public Card[] createCards(String version) throws IOException {
            // Create a card based on the version
            if ("Australia".equals(version)) {
                
                JsonArray cardsData = createJSONCardsFromConfig("../config/australianCards.JSON");
                this.arrayDeck = new Card [cardsData.size()];
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
                    //cards.add(new AustralianCard(name, letter, region, number, collection, animal, activity));
                    arrayDeck[i] = new AustralianCard(name, letter, region, number, collection, animal, activity);
                }
            } else if ("European".equals(version)) {
                // Create a European card (if applicable) and add it to the list
            } else {
                // Handle other versions or throw an exception for unsupported versions
                throw new IllegalArgumentException("Unsupported card version: " + version);
            }
        return arrayDeck;
    }
    public String[] createRegionsFromConfig(String version) throws IOException{
        // Create a card based on the version
            if ("Australia".equals(version)) {
                JsonArray regionsJsonArray = createRegionFromConfig("../config/australianCards.JSON");
                 Gson gson = new Gson();
                regions = gson.fromJson(regionsJsonArray, String[].class);
            } else if ("European".equals(version)) {
                // Create a European card (if applicable) and add it to the list
            } else {
                // Handle other versions or throw an exception for unsupported versions
                throw new IllegalArgumentException("Unsupported card version: " + version);
            }
        return regions;
    }

    private JsonArray createJSONCardsFromConfig(String configFilePath) throws IOException{
        FileReader fileReader = new FileReader(configFilePath);
        JsonObject config = JsonParser.parseReader(fileReader).getAsJsonObject();
        fileReader.close();

        // Extract the version from the JSON
        JsonArray cardsData= (JsonArray) config.get("cards");
        return cardsData;
    }
    private JsonArray createRegionFromConfig(String configFilePath) throws IOException{
        FileReader fileReader = new FileReader(configFilePath);
        JsonObject config = JsonParser.parseReader(fileReader).getAsJsonObject();
        fileReader.close();

        // Extract the version from the JSON
        JsonArray cardsData= (JsonArray) config.get("regions");
        return cardsData;
    }
}

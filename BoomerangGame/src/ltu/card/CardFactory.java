package ltu.card;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import com.google.gson.Gson;
public class CardFactory {
    private static ICardFactory cardCreator = null;

    public ICardFactory getCardFactory(String version) {
        if ("Australia".equals(version)) {
            cardCreator = new AustralianCardFactory();
            return cardCreator;
        } else if ("European".equals(version)) {
            // Create a European card (if applicable) and add it to the list
            // for now, not supported
            throw new IllegalArgumentException("Unsupported card version: " + version);

        } else {
            // Handle other versions or throw an exception for unsupported versions
            throw new IllegalArgumentException("Unsupported card version: " + version);
        }
    }

}

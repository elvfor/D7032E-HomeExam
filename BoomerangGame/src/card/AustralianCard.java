package card;
import com.google.gson.JsonObject;

public class AustralianCard extends Card{
    private String Collections, Animals, Activities;
    public AustralianCard(String name, String letter, String region, int number, String Collections, String Animals, String Activities) {
        super(name, letter, region, number);
        this.Collections = Collections;
        this.Animals = Animals;
        this.Activities = Activities;
    }

} 
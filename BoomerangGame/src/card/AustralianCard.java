package card;
import com.google.gson.JsonObject;

public class AustralianCard extends Card{
    private String Collection, Animal, Activity;
    public AustralianCard(String name, String letter, String region, int number, String Collection, String Animal, String Activity) {
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
} 
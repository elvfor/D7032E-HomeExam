package card;

public abstract class Card implements ICardFunctionalities{
    private String name, letter, region;
	private int number;
	public String sortMode;

	public Card(String name, String letter, String region, int number) {
		this.name=name; 
		this.letter=letter; 
		this.number=number; 
		this.region=region; 
	}

    public String getName() {
        return name;
    }

    public String getLetter() {
        return letter;
    }

    public String getRegion() {
        return region;
    }
	public int getNumber(){
		return number;
	}
}

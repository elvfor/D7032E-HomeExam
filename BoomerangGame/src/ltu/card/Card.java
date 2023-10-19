package ltu.card;

/**
 * This class is the main class that all cards should extend
 */
public abstract class Card implements ICardFunctionalities {
	private String name, letter, region;
	private int number;
	public String sortMode;

	public Card(String name, String letter, String region, int number) {
		this.name = name;
		this.letter = letter;
		this.number = number;
		this.region = region;
	}

	/**
	 * @return String get card's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return String get card's letter
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * @return String get card's regions
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return String get card's number
	 */
	public int getNumber() {
		return number;
	}
}

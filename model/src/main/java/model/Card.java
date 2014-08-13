package model;

public class Card {
	
	private final int ID;
	private final String WORD;
	private final String TRANSLATION;
		
	public Card(int id, String word, String translation) {
		ID = id;
		WORD = word;
		TRANSLATION = translation;
	}

	public int getID() {
		return ID;
	}

	public String getWORD() {
		return WORD;
	}

	public String getTRANSLATION() {
		return TRANSLATION;
	}
	
}

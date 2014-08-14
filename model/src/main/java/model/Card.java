package model;

public class Card {
	
	private int id;
	private String word;
	private String translation;
		
	public Card() {
	
	}
	
	public Card(int id, String word, String translation) {
		this.id = id;
		this.word = word;
		this.translation = translation;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public int getId() {
		return id;
	}

	public String getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}
	
}

package model;

public class Session {

	private String name;
	private Dictionary dictionary;
	private Card currentCard;
	
	public Session(String sessionName) {
		name = sessionName;
	}

	public String getName() {
		return name;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}
	
	public Card getCurrentCard() {
		return currentCard;
	}

	public Card nextCard() {
		return null;
	}
	
	public void saveAndExit() {
		
	}
	
}

package model;

public class Session {

	private String id;
	private Dictionary dictionary;
	private Card currentCard;
	private History sessionHistory;
	private User user;
	
	public Session() {
	
	}
	
	public Session(String sessionID) {
		id = sessionID;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public void setCurrentCard(Card currentCard) {
		this.currentCard = currentCard;
	}
	
	public void setSessionHistory(History sessionHistory) {
		this.sessionHistory = sessionHistory;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}
	
	public Card getCurrentCard() {
		return currentCard;
	}
	
	public History getSessionHistory() {
		return sessionHistory;
	}
	
	public User getUser() {
		return user;
	}
	
}

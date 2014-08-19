package model;

public class Card {
	
	private int id;
	private String word;
	private String translation;
	private String topic;
	private int priority;
		
	public Card(int id, String word, String translation, String topic, int priority) {
		this.id = id;
		this.word = word;
		this.translation = translation;
		this.topic = topic;
		this.priority = priority;
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
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
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
	
	public String getTopic() {
		return topic;
	}
	
	public int getPriority() {
		return priority;
	}
	
}

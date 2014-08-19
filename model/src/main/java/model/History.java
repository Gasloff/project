package model;

public class History {

	private int id;
	private int answered = 0;
	private int correct = 0;
	
	public History() {
		
	}
	
	public History(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnswered() {
		return answered;
	}

	public void setAnswered(int answered) {
		this.answered = answered;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public void incrementAnswered() {
		answered++;
	}
	
	public void incrementCorrect() {
		correct++;
	}
	
}

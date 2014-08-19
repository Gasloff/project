package model;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	private List<Card> cards;

	public Dictionary() {
		cards = new ArrayList<Card>();
	}
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
}

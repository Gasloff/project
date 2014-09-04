package db;

import java.util.List;

import model.Card;

public interface DictDAO {

	List<Card> readDict(String topic);
	
	Long addCard(Card card);
	
	public Long saveCard(Card card);
	
	public void saveList(List<Card> list);
}

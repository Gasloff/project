package db;

import java.util.List;

import model.Card;

/**
 * DictDAO interface provides access to Card objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface DictDAO {

	/**
	 * Returns list of Cards with given topic.
	 * 
	 * @param topic
	 *            - given topic
	 * @return List of Cards with given topic
	 */
	List<Card> readDict(String topic);

	/**
	 * Adds Card to data storage if it doesn't contain given Card. Overwrite
	 * existing one if given Card is already contains in data storage.
	 * 
	 * @param card
	 *            - given Card
	 * @return id of saved Card
	 */
	Long saveCard(Card card);

	/**
	 * Saves list of given Cards to data storage.
	 * 
	 * @param list
	 */
	void saveList(List<Card> list);

	/**
	 * Returns list of available topics
	 * @return
	 */
	List<String> readTopicList();
}

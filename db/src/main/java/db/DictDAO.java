package db;

import java.util.List;

import model.Card;

/**
 * DictDAO interface provides access to {@link Card} objects in some data
 * storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface DictDAO {

	/**
	 * Returns list of {@link Card} objects with given topic.
	 * 
	 * @param topic given topic
	 * @return List of {@link Card} objects with given <code>topic</code>
	 */
	List<Card> readDict(String topic);

	/**
	 * Adds {@link Card} to data storage if it doesn't contain given
	 * {@link Card}. Overwrite existing one if given {@link Card} is already
	 * contains in data storage.
	 * 
	 * @param card given {@link Card}
	 * @return <code>id</code> of saved {@link Card}
	 */
	Long saveCard(Card card);

	/**
	 * Saves list of given {@link Card} objects to data storage.
	 * 
	 * @param list
	 */
	void saveList(List<Card> list);

	/**
	 * Returns list of available topics
	 * 
	 * @return list of available topics
	 */
	List<String> readTopicList();
}

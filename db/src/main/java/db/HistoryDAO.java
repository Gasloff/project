package db;

import java.util.List;

import model.History;

/**
 * HistoryDAO interface provides access to History objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface HistoryDAO {

	/**
	 * Adds History to data storage if it doesn't contain given History.
	 * Overwrite existing one if given History is already contains in data
	 * storage.
	 * 
	 * @param history
	 *            - given History object
	 * @return id of saved History
	 */
	Long saveHistory(History history);

	/**
	 * Returns History with given id.
	 * 
	 * @param histID
	 *            - given id
	 * @return History object with given id
	 */
	History readHistory(Long histID);

	/**
	 * Returns list of History objects with given User.
	 * 
	 * @param userId
	 *            - id of given User
	 * @return list of History objects with given User
	 */
	List<History> getListByUser(Long userId);
}

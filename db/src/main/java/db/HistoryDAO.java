package db;

import java.util.List;

import model.History;

/**
 * HistoryDAO interface provides access to {@link History} objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface HistoryDAO {

	/**
	 * Adds {@link History} to data storage if it doesn't contain given {@link History}.
	 * Overwrite existing one if given {@link History} is already contains in data
	 * storage.
	 * 
	 * @param history
	 *            - given {@link History} object
	 * @return <code>histID</code> of saved {@link History}
	 */
	Long saveHistory(History history);

	/**
	 * Returns {@link History} with given id.
	 * 
	 * @param histID
	 *            - given id
	 * @return {@link History} object with given <code>histID</code>
	 */
	History readHistory(Long histID);

	/**
	 * Returns list of {@link History} objects with given User.
	 * 
	 * @param userId
	 *            - id of given {@link User}
	 * @return list of {@link History} objects with given {@link User}
	 */
	List<History> getListByUser(Long userId);
}

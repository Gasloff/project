package db;

import java.util.List;

import model.Study;
import model.User;

/**
 * StudyDAO interface provides access to {@link Study} objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface StudyDAO {

	/**
	 * Adds {@link Study} to data storage if it doesn't contain given {@link Study}. Overwrite
	 * existing one if given {@link Study} is already contains in data storage.
	 * 
	 * @param study given {@link Study} object
	 * @return <code>id</code> of saved {@link Study} object
	 */
	Long saveStudy(Study study);

	/**
	 * Deletes given {@link Study} object from data storage.
	 * 
	 * @param study given {@link Study} object
	 */
	void deleteStudy(Study study);

	/**
	 * Returns {@link Study} with given id.
	 * 
	 * @param studyID given id
	 * @return {@link Study} object with given <code>id</code>
	 */
	Study readStudy(Long studyID);

	/**
	 * Returns list of {@link Study} object owned by given {@link User}.
	 * 
	 * @param userId id of given {@link User}
	 * @return list of {@link Study} objects owned by {@link User} with given id
	 */
	List<Study> getListByUser(Long userId);

}

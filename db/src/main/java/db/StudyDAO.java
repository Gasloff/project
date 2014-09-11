package db;

import java.util.List;

import model.Study;

/**
 * StudyDAO interface provides access to Study objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface StudyDAO {

	/**
	 * Adds Study to data storage if it doesn't contain given Study. Overwrite
	 * existing one if given Study is already contains in data storage.
	 * 
	 * @param study
	 *            - given Study object
	 * @return id of save Study object
	 */
	Long saveStudy(Study study);

	/**
	 * Deletes given Study object from data storage.
	 * 
	 * @param study
	 *            - given Study object
	 */
	void deleteStudy(Study study);

	/**
	 * Returns Study with given id.
	 * 
	 * @param studyID
	 *            - given Study id
	 * @return Study object with given id
	 */
	Study readStudy(Long studyID);

	/**
	 * Returns list of Study object owned by given User.
	 * 
	 * @param userId
	 *            - id of given User
	 * @return list of Study objects owned by User with given id
	 */
	List<Study> getListByUser(Long userId);

}

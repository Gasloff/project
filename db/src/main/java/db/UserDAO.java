package db;

import java.util.List;

import model.User;

/**
 * UserDAO interface provides access to User objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface UserDAO {

	/**
	 * Adds given User to data storage.
	 * 
	 * @param user
	 *            - given User object
	 * @return id of added User
	 */
	Long addUser(User user);

	/**
	 * Returns User object with given login.
	 * 
	 * @param login
	 *            - given login
	 * @return User object with given login
	 */
	User loadUser(String login);

	/**
	 * Returns list of available Users.
	 * 
	 * @return list of stored User objects
	 */
	List<User> getUserList();
}

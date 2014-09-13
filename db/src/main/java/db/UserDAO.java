package db;

import java.util.List;

import model.User;

/**
 * UserDAO interface provides access to {@link User} objects in some data storage.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public interface UserDAO {

	/**
	 * Adds given {@link User} to data storage.
	 * 
	 * @param user
	 *            - given {@link User} object
	 * @return <code>userID</code> of added {@link User}
	 */
	Long addUser(User user);

	/**
	 * Returns {@link User} object with given login.
	 * 
	 * @param login
	 *            - given login
	 * @return User object with given <code>login</code>
	 */
	User loadUser(String login);

	/**
	 * Returns list of available Users.
	 * 
	 * @return list of stored {@link User} objects
	 */
	List<User> getUserList();
}

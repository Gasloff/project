package db;

import model.User;

public interface UserDAO {

	Long addUser(User user);
	
	User getUser(String login);
}

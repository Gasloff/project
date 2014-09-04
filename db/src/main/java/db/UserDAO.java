package db;

import java.util.List;

import model.User;

public interface UserDAO {

	Long addUser(User user);
	
	User loadUser(String login);
	
	List<User> getUserList();
}

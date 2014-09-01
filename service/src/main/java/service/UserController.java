package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import db.HistoryDAO;
import db.UserDAO;
import model.History;
import model.User;

public class UserController {
	
	private User user;
	
	@Autowired
	private UserDAO uDao;
	@Autowired
	private HistoryDAO histDao;
	
	
	public UserController() {}
	
	public User createUser(String login, String password) {
		user = new User(login, password);
		uDao.addUser(user);
		return user;
	}
	
	public User loadUser(String login) {
		user = uDao.getUser(login);
		return user;
	}
	
	public List<History> getHistList() {
		return histDao.getListByUser(user.getUserID());
	}
	
	public User getUser() {
		return user;
	}
	
}

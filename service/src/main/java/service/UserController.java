package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import db.DictDAO;
import db.HistoryDAO;
import db.UserDAO;
import model.Card;
import model.History;
import model.User;

public class UserController {
	
	private User user = null;
	private int defaultPriority = 2;
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private HistoryDAO histDao;
	@Autowired
	private DictDAO dictDao;
	
	public UserController() {}
	
	public User createUser(String login, String password) {
		user = new User(login, password);
		userDao.addUser(user);
		setNewUserPriority();
		return user;
	}
	
	public User loadUser(String login) {
		user = userDao.loadUser(login);
		return user;
	}
	
	public List<User> getUserList() {
		return userDao.getUserList();
	}
	
	public List<History> getHistList() {
		return histDao.getListByUser(user.getUserID());
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void populateDB() {

		User user = new User("Aleksandr", "6131");
		userDao.addUser(user);

		Card card1 = new Card("wolf", "волк", "animals");
		Card card2 = new Card("fox", "лиса", "animals");
		Card card3 = new Card("apple", "яблоко", "fruits");
		Card card4 = new Card("turtle", "черепаха", "animals");
		Card card5 = new Card("cat", "кошка", "animals");
		Card card6 = new Card("orange", "апельсин", "fruits");
		Card card7 = new Card("peach", "персик", "fruits");
		Card card8 = new Card("bear", "медведь", "animals");
		Card card9 = new Card("pear", "груша", "fruits");
		Card card10 = new Card("goat", "козел", "animals");

		card1.setPriority(user, 2);
		card2.setPriority(user, 2);
		card3.setPriority(user, 2);
		card4.setPriority(user, 2);
		card5.setPriority(user, 2);
		card6.setPriority(user, 2);
		card7.setPriority(user, 2);
		card8.setPriority(user, 2);
		card9.setPriority(user, 2);
		card10.setPriority(user, 2);

		dictDao.addCard(card1);
		dictDao.addCard(card2);
		dictDao.addCard(card3);
		dictDao.addCard(card4);
		dictDao.addCard(card5);
		dictDao.addCard(card6);
		dictDao.addCard(card7);
		dictDao.addCard(card8);
		dictDao.addCard(card9);
		dictDao.addCard(card10);
	}
	
	private void setNewUserPriority() {
		List<Card> list = dictDao.readDict("all");
		for (Card card : list) {
			card.setPriority(user, defaultPriority);
		}
		dictDao.saveList(list);
	}
	
}

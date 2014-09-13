package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.DictDAO;
import db.HistoryDAO;
import db.UserDAO;
import model.Card;
import model.History;
import model.User;

/**
 * UserService class provides methods for dealing with {@link User} and related
 * {@link History} objects.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Component
public class UserService {

	private User user = null;
	private final int DEFAULT_PRIORITY = 2;
	private final String ALL = "all";

	@Autowired
	private UserDAO userDao;
	@Autowired
	private HistoryDAO histDao;
	@Autowired
	private DictDAO dictDao;

	public UserService() {
	}

	/**
	 * Creates new {@link User} object with given login and password. Sets
	 * priority '2' for all {@link Card} objects for this new {@link User}.
	 * 
	 * @param login
	 *            - given login (user name)
	 * @param password
	 *            - given password
	 * @return new {@link User} object with given login and password
	 */
	public User createUser(String login, String password) {
		user = new User(login, password);
		userDao.addUser(user);
		setNewUserPriority();
		return user;
	}

	/**
	 * Loads {@link User} with given <code>login</code>.
	 * 
	 * @param login
	 *            - given login
	 * @return loaded {@link User} object
	 */
	public User loadUser(String login) {
		user = userDao.loadUser(login);
		return user;
	}

	/**
	 * Returns list of existing users.
	 * 
	 * @return list of existing {@link User} objects
	 */
	public List<User> getUserList() {
		return userDao.getUserList();
	}

	/**
	 * Returns list of saved {@link History} objects for current user.
	 * 
	 * @return list of {@link History} objects
	 */
	public List<History> getHistList() {
		return histDao.getListByUser(user.getUserID());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void setDictDao(DictDAO dictDao) {
		this.dictDao = dictDao;
	}

	/*
	 * Service method for populating database with cards and user
	 */
	public void populateDB() {

		User user = new User("Aleksandr", HashCode.getHashPassword("6131"));
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

		dictDao.saveCard(card1);
		dictDao.saveCard(card2);
		dictDao.saveCard(card3);
		dictDao.saveCard(card4);
		dictDao.saveCard(card5);
		dictDao.saveCard(card6);
		dictDao.saveCard(card7);
		dictDao.saveCard(card8);
		dictDao.saveCard(card9);
		dictDao.saveCard(card10);
	}

	/*
	 * Sets default priority for new User for all Cards in base
	 */
	private void setNewUserPriority() {
		List<Card> list = dictDao.readDict(ALL);
		for (Card card : list) {
			card.setPriority(user, DEFAULT_PRIORITY);
		}
		dictDao.saveList(list);
	}

}

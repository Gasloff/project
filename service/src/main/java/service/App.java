package service;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import db.DbDictDAO;
import db.DbUserDAO;
import db.DictDAO;
import db.UserDAO;
import model.Card;
import model.History;
import model.User;

/**
 * Temporary class for test runs
 * 
 * @author User
 * 
 */
@SuppressWarnings("unused")
public class App {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		// Turning off logging
		List<Logger> loggers = Collections.<Logger> list(LogManager
				.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for (Logger logger : loggers) {
			logger.setLevel(Level.OFF);
		}
		
		//populateDB();

		UserController uC = new UserController();
		uC.logIn();
		uC.startStudy();
	}

	public static void populateDB() {

		DbDictDAO dictDAO = new DbDictDAO();
		UserDAO userDAO = new DbUserDAO();

		Long dateMills = new java.util.Date().getTime();
		User user = new User("Aleksandr", "6131");
		History history = new History(user, new Date(dateMills), "-");
		user.setHistory(history);
		userDAO.addUser(user);

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

		dictDAO.addCard(card1);
		dictDAO.addCard(card2);
		dictDAO.addCard(card3);
		dictDAO.addCard(card4);
		dictDAO.addCard(card5);
		dictDAO.addCard(card6);
		dictDAO.addCard(card7);
		dictDAO.addCard(card8);
		dictDAO.addCard(card9);
		dictDAO.addCard(card10);

	}
}

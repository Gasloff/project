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
		
		//Turning off logging
		List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for ( Logger logger : loggers ) {
		    logger.setLevel(Level.OFF);
		}
		
		UserDAO userDAO = new DbUserDAO();
		/*
		DbDictDAO dictDAO = new DbDictDAO();
		
		User user = new User("Gaslov", "6131");
		
		Card cardOne = new Card("wolf", "волк", "animals");
		Card cardTwo = new Card("fox", "лиса", "animals");
		Card cardThree = new Card("apple", "яблоко", "fruits");
		Card cardFour = new Card("turtle", "черепаха", "animals");
		Card cardFive = new Card("cat", "кошка", "animals");
		Card cardSix = new Card("orange", "апельсин", "fruits");
		
		cardOne.setPriority(user, 1);
		cardTwo.setPriority(user, 1);
		cardThree.setPriority(user, 1);
		cardFour.setPriority(user, 1);
		cardFive.setPriority(user, 1);
		cardSix.setPriority(user, 1);
		
		dictDAO.addCard(cardOne);
		dictDAO.addCard(cardTwo);
		dictDAO.addCard(cardThree);
		dictDAO.addCard(cardFour);
		dictDAO.addCard(cardFive);
		dictDAO.addCard(cardSix);
		*/
		/*
		UserController uC = new UserController();
		uC.logIn();
		uC.startStudy();
		*/
		
		Long dateMills = new java.util.Date().getTime();
		User user = new User("Aleksandr", "6131");
		History history = new History(user, new Date(dateMills), "topic");
		user.setHistory(history);
		
		System.out.println(user.getLogin() + " : "
				+ user.getPassword() + " "
				+ user.getHistory().getTopic() + " "
				+ user.getHistory().getDate().toString() + " "
				+ user.getHistory().getAnswered() + " "
				+ user.getHistory().getCorrect() + " "
				+ user.getSavedStudies().toString());
		userDAO.addUser(user);
	}
}

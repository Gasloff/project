package service;

import java.io.IOException;
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
		
		DbDictDAO dictDAO = new DbDictDAO();
		UserDAO userDAO = new DbUserDAO();
		
		User user = new User("Gaslov", "6131");
		userDAO.addUser(user);
		SessionController sc = new SessionController(user);
		
		//sc.resumeStudy();
		//sc.startStudy();
		//sc.saveStudy();
		//sc.runStudy();
		
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
		/*
		List<Card> list = dictDAO.readDict("animals");
		for (Card c : list) {
			System.out.println(c.getWord() + " - " + c.getTranslation());
		}
		*/
	}

}

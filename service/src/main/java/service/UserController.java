package service;

import java.sql.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.DAOFactory;
import db.UserDAO;
import model.History;
import model.User;

public class UserController {
	
	private User user;
	
	private DAOFactory daoFactory;
	private UserInterface uI;
	private UserDAO uDAO;
	StudyController sC = new StudyController(user);
	
	public UserController() {
		ClassPathXmlApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");
		daoFactory = (DAOFactory) context.getBean("daoFactory");
		uI = (UserInterface) context.getBean("userInterface");
		context.close();
		uDAO = daoFactory.createUserDAO();
	}
	
	public User getUser() {
		return user;
	}

	public Long logIn() {
	
		String login;
		String password;
		if (uI.existingUser()) {
			login = uI.obtainLogin();
		} else {
			login = uI.obtainNewLogin();
			password = uI.obtainNewPassword();
			User newUser = new User(login, password);
			Long dateMills = new java.util.Date().getTime();
			History history = new History(newUser, new Date(dateMills), "-");
			newUser.setHistory(history);
			uDAO.addUser(newUser);
		}
		User candidate = uDAO.getUser(login);
		password = uI.obtainPassword();
		if (password.equals(candidate.getPassword())) {
			user = candidate;
		}
		return user.getUserID();	
	}
	
	public void startStudy() {
		if (uI.savedStudy()) {
			sC.resumeStudy();
		} else {
			sC.startStudy();
		}
	}
	
}

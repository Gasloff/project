package service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.DAOFactory;
import db.UserDAO;
import model.User;

public class UserController {
	
	private User user;
	
	private DAOFactory daoFactory;
	private UserInterface uI;
	private UserDAO uDAO;
	StudyController sC;
	
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
			uDAO.addUser(newUser);
		}
		User candidate = uDAO.getUser(login);
		password = uI.obtainPassword();
		if (password.equals(candidate.getPassword())) {
			user = candidate;
		}
		return user.getUserID();	
	}
	
}

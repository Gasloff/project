package service;

import java.io.IOException;

import model.User;

public class App {

	public static void main(String[] args) throws IOException {
		User user = new User(1, "Gaslov");
		SessionController sc = new SessionController(user);
		
		//sc.resumeSession();
		sc.startSession();
		//sc.saveSession();
		sc.runSession();
	}

}

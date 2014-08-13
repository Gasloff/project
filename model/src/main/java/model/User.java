package model;

import java.util.ArrayList;

public class User {

	private String login;
	private History history;
	private ArrayList<Session> savedSessions;
	
	public User(String login) {
		this.login = login;
	}
	
	public void startSession(String sessionName) {
		
	}
	
	public void resumeSession(String sessionName) {
		
	}

	public String getLogin() {
		return login;
	}

	public History getHistory() {
		return history;
	}

	public ArrayList<Session> getSavedSessions() {
		return savedSessions;
	}
	
	
}

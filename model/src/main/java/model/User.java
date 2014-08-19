package model;

import java.util.ArrayList;

public class User {

	private int id;
	private String login;
	private History history;
	private ArrayList<Session> savedSessions;
	
	public User() {
		
	}
	
	public User(int id, String login) {
		this.id = id;
		this.login = login;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public int getId() {
		return id;
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
	
	public void addSavedSession(Session savedSession) {
		this.savedSessions.add(savedSession);
	}
	
}

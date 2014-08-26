package model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_table")
public class User {

	private Long userID;
	private String login;
	private String password;
	private History history;
	private Set<Study> savedStudies = new HashSet<>(0);
	
	public User() {}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "user_id")
	public Long getUserID() {
		return userID;
	}

	@Column(name = "login")
	public String getLogin() {
		return login;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public History getHistory() {
		return history;
	}

	@OneToMany
	@JoinTable(name = "saved_studies")
	public Set<Study> getSavedStudies() {
		return savedStudies;
	}

	public void setUserID(Long user_id) {
		this.userID = user_id;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public void setSavedStudies(Set<Study> savedStudies) {
		this.savedStudies = savedStudies;
	}
	
}

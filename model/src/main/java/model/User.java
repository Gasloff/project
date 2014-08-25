package model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {

	private Long userID;
	private String login;
	private String password;
	private History history;
	private Set<Study> savedStudies;
	
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public History getHistory() {
		return history;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
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
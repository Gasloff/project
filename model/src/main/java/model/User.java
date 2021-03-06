package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * User class contains registration data - <code>login</code> and
 * <code>password</code>.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Entity
@Table(name = "user_table", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@NamedQueries({
		@NamedQuery(name = "findUserByLogin", query = "from User u where u.login = :login"),
		@NamedQuery(name = "allUsers", query = "from User u order by u.userID") })
public class User {

	private Long userID;
	private String login;
	private String password;

	public User() {
	}

	/**
	 * Returns new User object with given <code>login</code> and
	 * <code>password</code>.
	 * 
	 * @param login for new User
	 * @param password for new User
	 */
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

	public void setUserID(Long user_id) {
		this.userID = user_id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

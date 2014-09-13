package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * History class contains statistics for given {@link User}.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Entity
@Table(name = "history")
@NamedQuery(name = "allHistoryByUser", query = "from History h where h.user.userID = :userId order by h.histID")
public class History {

	private Long histID = -1L;
	private Integer answered = 0;
	private Integer correct = 0;
	private User user;
	private Date date;
	private String topic;

	public History() {
	}

	/**
	 * Returns new History object with given {@link User} and <code>topic</code>.
	 * @param user - {@link User} owning History object being created
	 * @param topic - topic of related {@link Study} 
	 */
	public History(User user, String topic) {
		this.user = user;
		this.date = new Date(System.currentTimeMillis());
		this.topic = topic;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "hist_id")
	public Long getHistID() {
		return histID;
	}

	@Column(name = "answered")
	public Integer getAnswered() {
		return answered;
	}

	@Column(name = "correct")
	public Integer getCorrect() {
		return correct;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	@Column(name = "topic")
	public String getTopic() {
		return topic;
	}

	public void setHistID(Long histID) {
		this.histID = histID;
	}

	public void setAnswered(Integer answered) {
		this.answered = answered;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/**
	 * Increments value of answered cards.
	 */
	public void incrementAnswered() {
		answered++;
	}

	/**
	 * Increments value of correct answers.
	 */
	public void incrementCorrect() {
		correct++;
	}

}

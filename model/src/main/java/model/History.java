package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "history")
public class History {

	private Long histID = -1L;
	private Integer answered = 0;
	private Integer correct = 0;
	private User user;
	private Date date;
	private String topic;
	
	public History() {}
	
	public History(User user, Date date, String topic) {
		this.user = user;
		this.date = date;
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

	public void incrementAnswered() {
		answered++;
	}
	
	public void incrementCorrect() {
		correct++;
	}
	
}

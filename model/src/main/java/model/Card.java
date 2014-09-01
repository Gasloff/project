package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "card")
public class Card {
	
	private Long id;
	private String word;
	private String translation;
	private String topic;
	private Set<Long> priorityOne = new HashSet<Long>(0);
	private Set<Long> priorityTwo = new HashSet<Long>(0);
	private Set<Long> priorityThree = new HashSet<Long>(0);
	
	public Card() {}
	
	public Card(String word, String translation, String topic) {
		this.word = word;
		this.translation = translation;
		this.topic = topic;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "card_id")
	public Long getId() {
		return id;
	}

	@Column(name = "word")
	public String getWord() {
		return word;
	}

	@Column(name = "translation")
	public String getTranslation() {
		return translation;
	}
	
	@Column(name = "card_topic")
	public String getTopic() {
		return topic;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="priority_one", joinColumns=@JoinColumn(name="card_id"))
	@Column(name="user_id")
	public Set<Long> getPriorityOne() {
		return priorityOne;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="priority_two", joinColumns=@JoinColumn(name="card_id"))
	@Column(name="user_id")
	public Set<Long> getPriorityTwo() {
		return priorityTwo;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="priority_three", joinColumns=@JoinColumn(name="card_id"))
	@Column(name="user_id")
	public Set<Long> getPriorityThree() {
		return priorityThree;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getPriority(User user) {
		Long userID = user.getUserID();
		Integer priority = -1;
		if (priorityOne.contains(userID)) {
			priority = 1;
		} else if (priorityTwo.contains(userID)) {
			priority = 2;
		} else if (priorityThree.contains(userID)) {
			priority = 3;
		}		
		return priority;
	}
	
	public void setPriority(User user, Integer priority) {
		switch (priority) {
		case 1:
			priorityOne.add(user.getUserID());
			break;
		case 2:
			priorityTwo.add(user.getUserID());
			break;
		case 3:
			priorityThree.add(user.getUserID());
			break;
		}
	}
	
	public void incrementPriority(User user) {
		Long userID = user.getUserID();
		if (priorityOne.contains(userID)) {
			priorityOne.remove(userID);
			priorityTwo.add(userID);
		} else if (priorityTwo.contains(userID)) {
			priorityTwo.remove(userID);
			priorityThree.add(userID);
		}
	}
}
	


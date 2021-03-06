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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * Card class contains <code>word</code>, <code>translation</code>,
 * <code>topic</code> and priorities. for all registered {@link User} objects.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Entity
@Table(name = "card", uniqueConstraints = @UniqueConstraint(columnNames = "word"))
@NamedQueries({
		@NamedQuery(name = "allCards", query = "from Card c order by c.id"),
		@NamedQuery(name = "findCardsByTopic", query = "from Card c where c.topic = :topic order by c.id"),
		@NamedQuery(name = "allTopics", query = "select distinct c.topic FROM Card c") })
public class Card {

	private static final long DEFAULT_ID = -1L;
	private static final String ILLEGAL_PRIORITY = "Not valid priority value. Should b in [1,2,3]";

	private Long id = DEFAULT_ID;
	private String word;
	private String translation;
	private String topic;
	private Set<Long> priorityOne = new HashSet<Long>(0);
	private Set<Long> priorityTwo = new HashSet<Long>(0);
	private Set<Long> priorityThree = new HashSet<Long>(0);

	public Card() {
	}

	/**
	 * Returns new Card object with given <code>word</code>,
	 * <code>translation</code> and <code>topic</code>.
	 * 
	 * @param word word for new Card
	 * @param translation translation for given word
	 * @param topic topic of given word
	 */
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
	@CollectionTable(name = "priority_one", joinColumns = @JoinColumn(name = "card_id"))
	@Column(name = "user_id")
	public Set<Long> getPriorityOne() {
		return priorityOne;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "priority_two", joinColumns = @JoinColumn(name = "card_id"))
	@Column(name = "user_id")
	public Set<Long> getPriorityTwo() {
		return priorityTwo;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "priority_three", joinColumns = @JoinColumn(name = "card_id"))
	@Column(name = "user_id")
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

	public void setPriorityOne(Set<Long> priorityOne) {
		this.priorityOne = priorityOne;
	}

	public void setPriorityTwo(Set<Long> priorityTwo) {
		this.priorityTwo = priorityTwo;
	}

	public void setPriorityThree(Set<Long> priorityThree) {
		this.priorityThree = priorityThree;
	}

	/**
	 * Returns priority of <code>this<code> Card for given {@link User}.
	 * 
	 * @param user given {@link User}
	 * @return <code>priority</code> for given {@link User}
	 */
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

	/**
	 * Sets given priority of owning Card for given {@link User}.
	 * 
	 * @param user given {@link User}
	 * @param priority value of priority for given {@link User}
	 */
	public void setPriority(User user, Integer priority) {
		priorityOne.remove(user);
		priorityTwo.remove(user);
		priorityThree.remove(user);
		
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
		default:
			throw new IllegalArgumentException(ILLEGAL_PRIORITY);
		}
	}

	/**
	 * Increments priority of owning Card for given {@link User}.
	 * 
	 * @param user given {@link User}
	 */
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

	/**
	 * Decrements priority of owning Card for given {@link User}.
	 * 
	 * @param user given {@link User}
	 */
	public void decrementPriority(User user) {
		Long userID = user.getUserID();
		if (priorityTwo.contains(userID)) {
			priorityTwo.remove(userID);
			priorityOne.add(userID);
		} else if (priorityThree.contains(userID)) {
			priorityThree.remove(userID);
			priorityTwo.add(userID);
		}
	}
}

package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * Study class contains topic of the study, order of cards, pointer to the
 * current card, link to the related {@link History} and {@link User} objects
 * and date of saving.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Entity
@Table(name = "study")
@NamedQuery(name = "allStudyByUser", query = "from Study s where s.user.userID = :userId order by s.id")
public class Study {

	private static final long DEFAULT_ID = -1L;
	
	private Long id = DEFAULT_ID;
	private String topic;
	private List<Integer> orderList = new ArrayList<Integer>();
	private int pointer = 0;
	private History history;
	private User user;
	private Date date;
	
	public Study() {
	}

	/**
	 * Returns new Study object with given <code>topic</code> and {@link User}.
	 * 
	 * @param topic topic for new study
	 * @param user {@link User} who owns Study being created
	 */
	public Study(String topic, User user) {
		this.topic = topic;
		this.user = user;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "study_id")
	public Long getId() {
		return id;
	}

	@Column(name = "study_topic")
	public String getTopic() {
		return topic;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "order_lists", joinColumns = @JoinColumn(name = "study_id"))
	@Column(name = "position")
	public List<Integer> getOrderList() {
		return orderList;
	}

	@Column(name = "pointer")
	public Integer getPointer() {
		return pointer;
	}

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public History getHistory() {
		return history;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setOrderList(List<Integer> orderList) {
		this.orderList = orderList;
	}

	public void setPointer(Integer pointer) {
		this.pointer = pointer;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Shifts pointer to the next card.
	 */
	public void incrementPointer() {
		pointer++;
	}

	/**
	 * Shifts pointer to the previous card.
	 */
	public void decrementPointer() {
		pointer--;
	}
}

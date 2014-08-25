package model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "study")
public class Study {

	private Long id;
	private String topic;
	private List<Integer> orderList = new ArrayList<>();
	private Integer pointer = 0;
	private History history;
	private User user;

	public Study() {
	}

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

	@ElementCollection
	@CollectionTable(name="order_lists", joinColumns=@JoinColumn(name="study_id"))
	@Column(name="position")
	public List<Integer> getOrderList() {
		return orderList;
	}

	@Column(name = "pointer")
	public Integer getPointer() {
		return pointer;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public History getHistory() {
		return history;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
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
	
	public void incrementPointer() {
		pointer++;
	}
	
}

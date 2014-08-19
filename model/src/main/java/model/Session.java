package model;

import java.util.ArrayList;
import java.util.List;

public class Session {

	private String id;
	private String topic;
	private List<Card> dictionary;
	private int orderListID = -1;
	private List<Integer> orderList = new ArrayList<>();
	private int pointer;
	private int historyID = -1;
	private History history;
		
	public Session() {
	
	}
	
	public Session(String sessionID, String topic) {
		id = sessionID;
		this.topic = topic;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<Card> getDictionary() {
		return dictionary;
	}

	public void setDictionary(List<Card> dictionary) {
		this.dictionary = dictionary;
	}

	public int getOrderListID() {
		return orderListID;
	}

	public void setOrderListID(int oListID) {
		this.orderListID = oListID;
	}

	public List<Integer> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Integer> orderList) {
		this.orderList = orderList;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	
	public void incrementPointer() {
		pointer++;
	}

	public int getHistoryID() {
		return historyID;
	}

	public void setHistoryID(int sessionHistoryID) {
		this.historyID = sessionHistoryID;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
			
}

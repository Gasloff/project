package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import db.DictDAO;
import db.FileDictDAO;
import db.FileHistoryDAO;
import db.FileSessionDAO;
import db.HistoryDAO;
import db.SessionDAO;
import model.Card;
import model.History;
import model.Session;
import model.User;

public class SessionController {

	private Session session;
	private User user;
	
	private CardController cardC = new ConsoleCardController();
	private ConsoleSessionInterface cSI = new ConsoleSessionInterface();
	private SessionDAO sDAO = new FileSessionDAO();
	private HistoryDAO histDAO = new FileHistoryDAO();
	
	public SessionController(User user) {
		this.user = user;
	}

	public void startSession() {
		String id = cSI.obtainSessionID();
		String topic = cSI.obtainTopic();
		session = new Session(id, topic);
		
		setSessionDict(topic);
		History history = new History();
		try {
			history = new History(histDAO.getNewID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		session.setHistory(history);
		prepareSequence();
	}
	
	public void resumeSession() {
		String savedID = cSI.obtainSavedSessionID();
		try {
			session = sDAO.readSession(savedID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSessionDict(session.getTopic());
		
	}

	public void runSession() {
		History history = session.getHistory();
		while (true) {
			Card card = nextCard();
			if (card == null)
				break;
			history.incrementAnswered();
			if (cardC.showCard(card)) {
				history.incrementCorrect();
			}
		}
		try {
			histDAO.saveHistory(history);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSession() {
		try {
			histDAO.saveHistory(session.getHistory());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			sDAO.saveSession(session);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	private void setSessionDict(String topic) {
		DictDAO dictDAO = new FileDictDAO();
		try {
			session.setDictionary(dictDAO.readDict(topic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareSequence() {
		List<Card> dict = session.getDictionary();
		List<Integer> orderList = session.getOrderList();
		List<Integer> priOne = new ArrayList<>();
		List<Integer> priTwo = new ArrayList<>();
		List<Integer> priThree = new ArrayList<>();

		for (int i = 0; i < dict.size(); i++) {
			Card c = dict.get(i);
			switch (c.getPriority()) {
			case 1:
				priOne.add(i);
				break;
			case 2:
				priOne.add(i);
				priTwo.add(i);
				break;
			case 3:
				priOne.add(i);
				priTwo.add(i);
				priThree.add(i);
				break;
			}
		}

		Collections.shuffle(priOne);

		if (priTwo.size() != 0) {
			Collections.shuffle(priTwo);
			while (priOne.get(priOne.size() - 1) == priTwo.get(0)
					|| priOne.get(priOne.size() - 2) == priTwo.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
			orderList.addAll(priTwo);
		} else {
			int oneBeforeLast = priOne.get(priOne.size() - 2);
			int oneLast = priOne.get(priOne.size() - 1);
			while (oneLast == priOne.get(0) || oneLast == priOne.get(1)
					|| oneBeforeLast == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
		}

		Collections.shuffle(priOne);
		if (priThree.size() != 0) {
			Collections.shuffle(priThree);
			while (priOne.get(priOne.size() - 1) == priThree.get(0)
					|| priOne.get(priOne.size() - 2) == priThree.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
			orderList.addAll(priThree);
		} else {
			orderList.addAll(priOne);
		}

		for (int i : orderList) {
			System.out.println(i);
		}
	}

	private Card nextCard() {
		List<Card> dict = session.getDictionary();
		List<Integer> orderList = session.getOrderList();
		int pointer = session.getPointer();
		int cardNumber;
		session.incrementPointer();

		Card card;
		if (pointer < orderList.size()) {
			cardNumber = orderList.get(pointer);
			card = dict.get(cardNumber);
		} else {
			card = null;
		}
		return card;
	}

}

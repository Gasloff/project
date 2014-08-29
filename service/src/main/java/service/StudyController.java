package service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.DAOFactory;
import db.DictDAO;
import db.HistoryDAO;
import db.StudyDAO;
import model.Card;
import model.History;
import model.Study;
import model.User;

public class StudyController {

	private Study study;
	private User user;
	private List<Card> dict;
	private CardController cardC = new ConsoleCardController();
	private StudyDAO sDAO;
	private DictDAO dictDAO;
	private HistoryDAO histDAO;

	private DAOFactory daoFactory;

	public StudyController(User user) {
		this.user = user;
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"Beans.xml");
		daoFactory = (DAOFactory) context.getBean("daoFactory");
		sDAO = daoFactory.createStudyDAO();
		dictDAO = daoFactory.createDictDAO();
		context.close();
	}

	public Study createStudy(String topic, User user) {
		study = new Study(topic, user);
		Long dateMills = new java.util.Date().getTime();
		History history = new History(user, new Date(dateMills), topic);
		study.setHistory(history);
		dict = dictDAO.readDict(topic);
		prepareSequence();

		return study;
	}

	public Study loadStudy(Long studyID) {
		study = sDAO.readStudy(studyID);
		dict = dictDAO.readDict(study.getTopic());
		return study;
	}

	public Long saveStudy() {
		Long savedID = null;
		savedID = sDAO.saveStudy(study);

		return savedID;
	}

	public Card nextCard() {
		List<Integer> orderList = study.getOrderList();
		int pointer = study.getPointer();
		int cardNumber;
		study.incrementPointer();

		Card card;
		if (pointer < orderList.size()) {
			cardNumber = orderList.get(pointer);
			card = dict.get(cardNumber);
		} else {
			card = null;
		}
		return card;
	}

	public void runStudy() {
		histDAO = daoFactory.createHistoryDAO();

		History history = study.getHistory();
		while (true) {
			Card card = nextCard();
			if (card == null)
				break;

			int response = cardC.showCard(card, study.getUser());
			if (response == 1) {
				history.incrementCorrect();
			} else if (response == 2) {
				study.setPointer(study.getPointer() - 1);
				saveStudy();
				break;
			}
			history.incrementAnswered();
		}
		try {
			histDAO.saveHistory(history);
		} catch (IOException e) {
			e.printStackTrace();
		}
		study = null;
	}

	public User getUser() {
		return user;
	}
	
	public Study getStudy() {
		return study;
	}
	
	public List<Card> getDict() {
		return dict;
	}

	/*
	 * Prepares random sequence based on priority.
	 * Order is: priority "1" --> priority "2" --> priority "1" --> priority "3"
	*/
	private void prepareSequence() {
		List<Integer> orderList = study.getOrderList();
		List<Integer> priOne = new ArrayList<Integer>();
		List<Integer> priTwo = new ArrayList<Integer>();
		List<Integer> priThree = new ArrayList<Integer>();

		for (int i = 0; i < dict.size(); i++) {
			Card card = dict.get(i);
			priOne.add(i);
			switch (card.getPriority(user)) {
			case 2:
				priTwo.add(i);
				break;
			case 3:
				priTwo.add(i);
				priThree.add(i);
				break;
			}
		}
		
		Collections.shuffle(priOne);

		if (priTwo.size() > 0) {
			Collections.shuffle(priTwo);
			boolean flag = false;
			if (priTwo.size() == 1) {
				flag = true;
				priTwo.add(-1);
			}
			while (priOne.get(priOne.size() - 1) == priTwo.get(0)
					|| priOne.get(priOne.size() - 1) == priTwo.get(1)
					|| priOne.get(priOne.size() - 2) == priTwo.get(0)
					) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
			if (flag) {
				priTwo.remove(1);
			}
			orderList.addAll(priTwo);
		} else {
			while (priOne.get(priOne.size() - 1) == priOne.get(0)
					|| priOne.get(priOne.size() - 1) == priOne.get(1)
					|| priOne.get(priOne.size() - 2) == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
		}

		Collections.shuffle(priOne);
		if (priThree.size() > 0) {
			Collections.shuffle(priThree);
			boolean flag = false;
			if (priThree.size() == 1) {
				flag = true;
				priThree.add(-1);
			}
			while (priOne.get(priOne.size() - 1) == priThree.get(0)
					|| priOne.get(priOne.size() - 1) == priThree.get(1)
					|| priOne.get(priOne.size() - 2) == priThree.get(0)
					|| orderList.get(priOne.size() - 1) == priOne.get(0)
					|| orderList.get(priOne.size() - 1) == priOne.get(1)
					|| orderList.get(priOne.size() - 2) == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
			if (flag) {
				priThree.remove(1);
			}
			orderList.addAll(priThree);
		} else {
			while (orderList.get(priOne.size() - 1) == priOne.get(0)
					|| orderList.get(priOne.size() - 1) == priOne.get(1)
					|| orderList.get(priOne.size() - 2) == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
		}
		
	}

}

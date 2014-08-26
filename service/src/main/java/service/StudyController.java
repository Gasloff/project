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
	private ConsoleStudyInterface cSI = new ConsoleStudyInterface();
	private StudyDAO sDAO;
	private DictDAO dictDAO;
	private HistoryDAO histDAO;
	
	private DAOFactory daoFactory;
	
	public StudyController(User user) {
		this.user = user;
		
		ClassPathXmlApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		daoFactory = (DAOFactory) context.getBean("daoFactory");
		context.close();
	}

	/**
	 * Obtains topic and creates new Study with obtained topic, current User,
	 * current Date, new blank History and prepares sequence of Cards via
	 * prepareSequence() method.  
	 */
	public void startStudy() {
		String topic = cSI.obtainTopic();
		study = new Study(topic, user);
		setStudyDict(topic);
		
		Long dateMills = new java.util.Date().getTime();
		History history = new History(user, new Date(dateMills), topic);
		study.setHistory(history);
		
		prepareSequence();
	}
	
	public void resumeStudy() {
		sDAO = daoFactory.createStudyDAO();
		Long savedID = cSI.obtainSavedStudyID();
		try {
			study = sDAO.readStudy(savedID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setStudyDict(study.getTopic());
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
	
	public Long saveStudy() {
		sDAO = daoFactory.createStudyDAO();
		Long savedID = null;
		try {
			savedID = sDAO.saveStudy(study);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return savedID;
	}

	public User getUser() {
		return user;
	}

	private void setStudyDict(String topic) {
		dictDAO = daoFactory.createDictDAO();
		try {
			dict = dictDAO.readDict(topic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareSequence() {
		List<Integer> orderList = study.getOrderList();
		List<Integer> priOne = new ArrayList<Integer>();
		List<Integer> priTwo = new ArrayList<Integer>();
		List<Integer> priThree = new ArrayList<Integer>();

		for (int i = 0; i < dict.size(); i++) {
			Card card = dict.get(i);
			switch (card.getPriority(user)) {
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

	}

	private Card nextCard() {
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

}

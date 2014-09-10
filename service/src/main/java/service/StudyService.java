package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import db.DictDAO;
import db.HistoryDAO;
import db.StudyDAO;
import model.Card;
import model.History;
import model.Study;
import model.User;

public class StudyService {

	private Study study;
	private User user;
	private List<Card> dict;
	private List<Integer> correctList = new ArrayList<>();
	private boolean loaded = false;

	@Autowired
	private StudyDAO studyDAO;
	@Autowired
	private DictDAO dictDAO;
	@Autowired
	private HistoryDAO histDAO;

	public StudyService() {
	}

	public StudyService(User user) {
		this.user = user;
	}

	/**
	 * Creates new Study object with given topic and user.
	 * Creates corresponding History object with current date.
	 * @param topic Topic for the new Study
	 * @param user User for the new Study
	 * @return new Study object
	 */
	public Study createStudy(String topic, User user) {
		study = new Study(topic, user);
		History history = new History(user,
				new Date(System.currentTimeMillis()), topic);
		study.setHistory(history);
		createDict(topic);
		prepareSequence();
		loaded = false;
		return study;
	}

	/**
	 * Returns list of available Card's topics
	 * @return list of topics
	 */
	public List<String> readTopicList() {
		return dictDAO.readTopicList();
	}

	/**
	 * Loads previously saved study with given id
	 * @param studyId id of given saved study
	 * @return Study object
	 */
	public Study loadStudy(Long studyId) {
		study = studyDAO.readStudy(studyId);
		createDict(study.getTopic());
		loaded = true;
		return study;
	}

	/**
	 * Returns list of saved studies for given user.
	 * @param userId id of given user
	 * @return list of saved studies
	 */
	public List<Study> loadListByUser(Long userId) {
		List<Study> studyList = studyDAO.getListByUser(userId);
		return studyList;
	}

	
	/**
	 * Saves current study with current date.
	 * @return saved study id
	 */
	public Long saveStudy() {
		Long savedID = null;
		Date date = new Date(System.currentTimeMillis());
		study.setDate(date);
		savedID = studyDAO.saveStudy(study);
		return savedID;
	}

	/**
	 * Returns next Card from previously prepared shuffled list.
	 * Cards with priority '3' appears 4 times per study.
	 * Cards with priority '2' appears 3 times per study.
	 * Cards with priority '1' appears 2 times per study.
	 * @return next Card according its priority or null if there is no next Card 
	 */
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
			if (loaded) {
				studyDAO.deleteStudy(study);
				History history = study.getHistory();
				history.setHistID(-1L);
				histDAO.saveHistory(history);
			}
		}
		return card;
	}

	/**
	 * Returns if received answer match Card's translation. Increments counter
	 * of questions of the corresponding History object. If answer is correct,
	 * increments counter of correct answers of the corresponding History
	 * object. If correct answer is second in succession for given Card, Card's
	 * priority decrements. If answer is not correct, Card's priority
	 * increments.
	 * 
	 * @param card - Card being answered
	 * @param answer - user's answer 
	 * @return true if answer is correct, false otherwise
	 */
	public boolean processAnswer(Card card, String answer) {
		History history = study.getHistory();
		history.incrementAnswered();
		if (answer.equals(card.getTranslation())) {
			history.incrementCorrect();
			int index = dict.indexOf(card);
			int counter = correctList.get(index);
			if (counter == 1) {
				correctList.set(index, 0);
				card.decrementPriority(user);
				dictDAO.saveCard(card);
			} else {
				counter++;
				correctList.set(index, counter);
			}
			return true;
		} else {
			card.incrementPriority(user);
			int index = dict.indexOf(card);
			correctList.set(index, 0);
			dictDAO.saveCard(card);
			return false;
		}
	}

	public Long saveHistory() {
		History history = study.getHistory();
		return histDAO.saveHistory(history);
	}

	public History getHistory() {
		return study.getHistory();
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

	public boolean isLoaded() {
		return loaded;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	/*
	 * Prepares list of Cards with specified topic. Prepares list of Integers,
	 * where Integer is a number of correct answers in succession.
	 */
	private void createDict(String topic) {
		dict = dictDAO.readDict(topic);
		for (int i = 0; i < dict.size(); i++) {
			correctList.add(0);
		}
	}

	/*
	 * Prepares random sequence based on priority. Order is: priority "1" -->
	 * priority "2" --> priority "1" --> priority "3".
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
					|| priOne.get(priOne.size() - 2) == priTwo.get(0)) {
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

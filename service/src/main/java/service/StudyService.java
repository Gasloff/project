package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.DictDAO;
import db.HistoryDAO;
import db.StudyDAO;
import model.Card;
import model.History;
import model.Study;
import model.User;

/**
 * StudyService class provides methods for dealing with {@link Study} objects,
 * getting list of available topics, getting next {@link Card} object from some
 * shuffled sequence and processing user's answers.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Component
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
	 * Creates new {@link Study} object with given <code>topic</code> and
	 * <code>user</code>. Creates corresponding {@link History} object with
	 * current date.
	 * 
	 * @param topic
	 *            - topic for the new {@link Study}
	 * @param user
	 *            - {@link User} for the new {@link Study}
	 * @return new {@link Study} object
	 */
	public Study createStudy(String topic, User user) {
		study = new Study(topic, user);
		History history = new History(user, topic);
		study.setHistory(history);
		createDict(topic);
		prepareSequence();
		loaded = false;
		return study;
	}

	/**
	 * Returns list of available {@link Card} topics.
	 * 
	 * @return list of topics
	 */
	public List<String> readTopicList() {
		return dictDAO.readTopicList();
	}

	/**
	 * Loads previously saved {@link Study} with given id.
	 * 
	 * @param studyId
	 *            id of given saved {@link Study}
	 * @return {@link Study} object
	 */
	public Study loadStudy(Long studyId) {
		study = studyDAO.readStudy(studyId);
		createDict(study.getTopic());
		loaded = true;
		return study;
	}

	/**
	 * Returns list of saved {@link Study} objects for given {@link User}.
	 * 
	 * @param userId
	 *            id of given {@link User}
	 * @return list of saved {@link Study} objects
	 */
	public List<Study> loadListByUser(Long userId) {
		List<Study> studyList = studyDAO.getListByUser(userId);
		return studyList;
	}

	/**
	 * Saves current {@link Study} objects with current date.
	 * 
	 * @return saved {@link Study} <code>id</code>
	 */
	public Long saveStudy() {
		Long savedID = null;
		Date date = new Date(System.currentTimeMillis());
		study.setDate(date);
		savedID = studyDAO.saveStudy(study);
		return savedID;
	}

	/**
	 * Returns next {@link Card} from previously prepared shuffled list. Cards
	 * with priority '3' appears 4 times per study. Cards with priority '2'
	 * appears 3 times per study. Cards with priority '1' appears 2 times per
	 * study.
	 * 
	 * @return next {@link Card} according its priority or null if there is no
	 *         next {@link Card}
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
	 * Returns if received answer matches {@link Card} <code>translation</code>.
	 * Increments counter of <code>answered</code> questions of the
	 * corresponding {@link History} object. If answer is correct, increments
	 * counter of <code>correct</code> answers of the corresponding
	 * {@link History} object. If correct answer is second in succession for
	 * given {@link Card}, Card's priority decrements. If answer is not correct,
	 * Card's priority increments.
	 * 
	 * @param card
	 *            - {@link Card} being answered
	 * @param answer
	 *            - user's answer
	 * @return <code>true</code> if answer is correct, <code>false</code>
	 *         otherwise
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

	public List<Integer> getCorrectList() {
		return correctList;
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

	public void setDict(List<Card> dict) {
		this.dict = dict;
	}

	public void setCorrectList(List<Integer> correctList) {
		this.correctList = correctList;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public void setStudyDAO(StudyDAO studyDAO) {
		this.studyDAO = studyDAO;
	}

	public void setDictDAO(DictDAO dictDAO) {
		this.dictDAO = dictDAO;
	}

	public void setHistDAO(HistoryDAO histDAO) {
		this.histDAO = histDAO;
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
					|| orderList.get(orderList.size() - 1) == priOne.get(0)
					|| orderList.get(orderList.size() - 1) == priOne.get(1)
					|| orderList.get(orderList.size() - 2) == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
			if (flag) {
				priThree.remove(1);
			}
			orderList.addAll(priThree);
		} else {
			while (orderList.get(priOne.size() - 1) == priOne.get(0)
					|| orderList.get(orderList.size() - 1) == priOne.get(1)
					|| orderList.get(orderList.size() - 2) == priOne.get(0)) {
				Collections.shuffle(priOne);
			}
			orderList.addAll(priOne);
		}

	}

}

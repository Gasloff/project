package service;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.History;
import model.Study;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DictDAO;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestStudyService {

	StudyService testStudyService;
	User testUser;
	String testTopic;
	List<Card> listCards;
	List<Card> shortListCards;
	List<Integer> testCorrectList;
	List<Integer> testOrderList;

	private final Integer ZERO = new Integer(0);
	private final Integer ONE = new Integer(1);
	private final Integer TWO = new Integer(2);
	private final Integer THREE = new Integer(3);
	private int orderListSize;

	@Before
	public void setUp() {

		testUser = new User("Test", "pass");
		testUser.setUserID(1L);
		testTopic = "topic";

		Card card1 = new Card("one", "1", testTopic);
		Card card2 = new Card("two", "2", testTopic);
		Card card3 = new Card("three", "3", testTopic);
		Card card4 = new Card("four", "4", testTopic);
		Card card5 = new Card("five", "5", testTopic);
		Card card6 = new Card("six", "6", testTopic);

		card1.setPriority(testUser, 1);
		card2.setPriority(testUser, 1);
		card3.setPriority(testUser, 2);
		card4.setPriority(testUser, 2);
		card5.setPriority(testUser, 3);
		card6.setPriority(testUser, 3);

		/*
		 * Order list contains cards with priority 1 two times, cards with
		 * priority 2 three times cards with priority 3 four times
		 * 
		 * two cards with priority 1, two cards with priority 2, two cards with
		 * priority 3
		 */
		orderListSize = 2 * 2 + 2 * 3 + 2 * 4;

		listCards = new ArrayList<>();
		shortListCards = new ArrayList<>();

		listCards.add(card1);
		listCards.add(card2);
		listCards.add(card3);
		listCards.add(card4);
		listCards.add(card5);
		listCards.add(card6);

		shortListCards.add(card1);
		shortListCards.add(card3);
		shortListCards.add(card5);

		testCorrectList = new ArrayList<>();
		testCorrectList.add(1);
		testCorrectList.add(0);
		testCorrectList.add(0);

		testOrderList = new ArrayList<Integer>();
		testOrderList.add(0);
		testOrderList.add(1);
		testOrderList.add(2);

		DictDAO mockDictDAO = mock(DictDAO.class);
		when(mockDictDAO.readDict(testTopic)).thenReturn(listCards);
		when(mockDictDAO.saveCard((Card) any())).thenReturn(-1L);

		testStudyService = new StudyService(testUser);
		testStudyService.setDictDAO(mockDictDAO);
	}

	@After
	public void tearDown() {

		testStudyService = null;
		testUser = null;
		testTopic = null;
		listCards = null;
		shortListCards = null;
		testCorrectList = null;
		testOrderList = null;
	}

	@Test
	public void testCreateStudy() {

		Study testStudy = testStudyService.createStudy(testTopic, testUser);
		testOrderList = testStudy.getOrderList();
		
		// In correct order list two identical cards should be separated at
		// least by two another cards
		boolean rightOrder = true;
		for (int i = 0; i < testOrderList.size() - 3; i++) {
			if (testOrderList.get(i).equals(testOrderList.get(i + 1))
					|| testOrderList.get(i).equals(testOrderList.get(i + 2))) {
				rightOrder = false;
			}
		}

		assertEquals(testTopic, testStudy.getTopic());
		assertEquals(testUser, testStudy.getUser());
		assertEquals(listCards, testStudyService.getDict());
		assertEquals(orderListSize, testOrderList.size());
		assertEquals(true, rightOrder);
	}

	@Test
	public void testNextCard() {

		testStudyService.setStudy(new Study());
		testStudyService.getStudy().setOrderList(testOrderList);
		testStudyService.setDict(shortListCards);

		// First card in listCards has priority 1
		assertEquals(ONE, testStudyService.nextCard().getPriority(testUser));
		// Second card in listCards has priority 2
		assertEquals(TWO, testStudyService.nextCard().getPriority(testUser));
		// Third card in listCards has priority 3
		assertEquals(THREE, testStudyService.nextCard().getPriority(testUser));
		// There is no cards in listCards, method should return null
		assertEquals(null, testStudyService.nextCard());
	}

	@Test
	public void testProcessAnswer() {

		testStudyService.setStudy(new Study());
		testStudyService.getStudy().setHistory(new History());
		testStudyService.setDict(shortListCards);
		testStudyService.setCorrectList(testCorrectList);

		Card testCard = shortListCards.get(0);
		final String WRONG = testCard.getTranslation().concat(" - wrong");
		final String RIGHT = testCard.getTranslation();

		assertEquals(false, testStudyService.processAnswer(testCard, WRONG));
		// After wrong answer card's priority should be increased
		assertEquals(TWO, testCard.getPriority(testUser));
		// After wrong answer number of correct answers in success should be set
		// to zero
		assertEquals(ZERO, testStudyService.getCorrectList().get(0));

		assertEquals(true, testStudyService.processAnswer(testCard, RIGHT));
		// After correct answer number of correct answers in success should be
		// increased
		assertEquals(ONE, testStudyService.getCorrectList().get(0));
		assertEquals(true, testStudyService.processAnswer(testCard, RIGHT));
		// After second correct answer in success number of correct answers in
		// success should be set to zero
		assertEquals(ZERO, testStudyService.getCorrectList().get(0));
		// After second correct answer in success card's priority should be
		// decreased
		assertEquals(ONE, testCard.getPriority(testUser));
	}

}

package service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.History;
import model.Study;
import model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import db.DictDAO;

/**
 * StudyServiceUnitTest provides testing {@link StudyService} class.
 * 
 * @author Aleksandr Gaslov
 *
 */
public class StudyServiceUnitTest {

	private User testUser;
	private String testTopic;
	private List<Card> listCards;
	private List<Card> shortListCards;
	private List<Integer> testCorrectList;
	private List<Integer> testOrderList;
		
	@Mock
	private DictDAO mockDictDAO;
	@InjectMocks
	private StudyService testStudyService;
	
	private static final Integer ZERO = new Integer(0);
	private static final Integer ONE = new Integer(1);
	private static final Integer TWO = new Integer(2);
		
	/**
	 * Sets up necessary test objects and mocks.
	 */
	@Before
	public void setUp() {

		testUser = new User("Test", "pass");
		testUser.setUserID(1L);
		testTopic = "topic";
		
		testStudyService = new StudyService(testUser);

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
		

		listCards = new ArrayList<Card>();
		shortListCards = new ArrayList<Card>();

		listCards.add(card1);
		listCards.add(card2);
		listCards.add(card3);
		listCards.add(card4);
		listCards.add(card5);
		listCards.add(card6);
		
		
		shortListCards.add(card1);
		shortListCards.add(card3);
		shortListCards.add(card5);

		testCorrectList = new ArrayList<Integer>();
		testCorrectList.add(1);
		testCorrectList.add(0);
		testCorrectList.add(0);

		testOrderList = new ArrayList<Integer>();
		testOrderList.add(0);
		testOrderList.add(1);
		testOrderList.add(2);

		MockitoAnnotations.initMocks(this);
		Mockito.when(mockDictDAO.readDict(testTopic)).thenReturn(listCards);
		Mockito.when(mockDictDAO.saveCard((Card) any())).thenReturn(-1L);
	}

	/**
	 * Tests {@link StudyService#createStudy(String, User)} method.
	 */
	@Test
	public void testCreateStudy() {

		Study testStudy = testStudyService.createStudy(testTopic, testUser);
		testOrderList = testStudy.getOrderList();
		
		assertEquals(testTopic, testStudy.getTopic());
		assertEquals(testUser, testStudy.getUser());
		assertEquals(listCards, testStudyService.getDict());
		assertFalse(testStudyService.isLoaded());
	
	}

	/**
	 * Tests {@link StudyService#nextCard()} method. Checks if correct card is
	 * returned or null if there are no cards left.
	 */
	@Test
	public void testNextCard() {

		testStudyService.setStudy(new Study());
		testStudyService.getStudy().setOrderList(testOrderList);
		testStudyService.setDict(shortListCards);

		// First card in shortlistCards should be returned
		assertEquals(shortListCards.get(0), testStudyService.nextCard());
		// Second card in shortlistCards should be returned
		assertEquals(shortListCards.get(1), testStudyService.nextCard());
		// Third card in shortlistCards should be returned
		assertEquals(shortListCards.get(2), testStudyService.nextCard());
		// There are no cards in shortlistCards, method should return null
		assertEquals(null, testStudyService.nextCard());
	}
	
	/**
	 * Tests {@link StudyService#processAnswer(Card, String)} method.<br>
	 * After wrong answer card's priority should be increased and number of
	 * correct answers in success should be set to zero.<br>
	 * After correct answer number of correct answers in success should be
	 * increased.<br>
	 * After second correct answer in success card's priority should be
	 * decreased and number of correct answers in success should be set to zero.
	 */
	@Test
	public void testProcessAnswer() {

		testStudyService.setStudy(new Study());
		testStudyService.getStudy().setHistory(new History());
		testStudyService.setDict(shortListCards);
		testStudyService.setCorrectList(testCorrectList);

		Card testCard = shortListCards.get(0);
		final String WRONG = testCard.getTranslation().concat(" - wrong");
		final String RIGHT = testCard.getTranslation();

		assertFalse(testStudyService.processAnswer(testCard, WRONG));
		// After wrong answer card's priority should be increased
		assertEquals(TWO, testCard.getPriority(testUser));
		// After wrong answer number of correct answers in success should be set
		// to zero
		assertEquals(ZERO, testStudyService.getCorrectList().get(0));

		assertTrue(testStudyService.processAnswer(testCard, RIGHT));
		// After correct answer number of correct answers in success should be
		// increased
		assertEquals(ONE, testStudyService.getCorrectList().get(0));
		assertTrue(testStudyService.processAnswer(testCard, RIGHT));
		// After second correct answer in success number of correct answers in
		// success should be set to zero
		assertEquals(ZERO, testStudyService.getCorrectList().get(0));
		// After second correct answer in success card's priority should be
		// decreased
		assertEquals(ONE, testCard.getPriority(testUser));
	}
	
	/**
	 * Tests {@link StudyService} <code>createDict()<code> protected method.
	 * Checks if number of correct answers by success for all cards in dictionary
	 * is set to zero. 
	 */
	@Test
	public void testCreateDict() {

		testStudyService.createDict(testTopic);
		boolean rightCorrectList = true; 
		for (int i : testStudyService.getCorrectList()) {
			if (i != 0) {
				rightCorrectList = false;
			}
		}
		assertEquals(listCards.size(), testStudyService.getCorrectList().size());
		assertTrue(rightCorrectList);
	}
}

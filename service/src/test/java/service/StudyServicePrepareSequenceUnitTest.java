package service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import model.Card;
import model.Study;
import model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * StudyServicePrepareSequenceUnitTest class provides parameterized testing of
 * {@link StudyService} protected <code>prepareSequence()</code> method with different dictionaries: <br>
 *  - cards only with priority '1' <br>
 *  - cards with priorities '1' and '2' <br>
 *  - cards with priorities '1' and '3' <br>
 *  - cards with priorities '1', '2' and '3' <br>
 *  - cards with priorities '2' and '3' <br>
 *  - cards only with priority '2' <br>
 *  - cards only with priority '3' <br>
 *  - cards with priority '1' with one card with priority '2' <br>
 *  - cards with priority '1' with one card with priority '3' <br>
 * 
 * @author Aleksandr Gaslov
 * 
 */
@RunWith(value = Parameterized.class)
public class StudyServicePrepareSequenceUnitTest {

	private static StudyService testStudyService;
	private static User testUser;
	private static String testTopic;
	private static List<Card> listCards1;
	private static List<Card> listCards2;
	private static List<Card> listCards3;
	private static List<Card> listCards4;
	private static List<Card> listCards5;
	private static List<Card> listCards6;
	private static List<Card> listCards7;
	private static List<Card> listCards8;
	private static List<Card> listCards9;
	private static List<Card> listCards10;
	private List<Integer> testOrderList;

	private List<Card> inputList;
	private int orderListSize;

	public StudyServicePrepareSequenceUnitTest(List<Card> inputList,
			int orderListSize) {
		this.inputList = inputList;
		this.orderListSize = orderListSize;
	}

	/**
	 * Returns Collection of pairs of parameters for testing. First parameter is
	 * dictionary, second is size of sequence, that should be processed from
	 * given dictionary.
	 * 
	 * @return Collection of pairs of parameters for testing
	 */
	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection sampleLists() {

		testUser = new User("Test", "pass");
		testUser.setUserID(1L);
		testTopic = "topic";
		testStudyService = new StudyService(testUser);

		Card card0 = new Card("zero", "0", testTopic);
		Card card1 = new Card("one", "1", testTopic);
		Card card2 = new Card("two", "2", testTopic);
		Card card3 = new Card("three", "3", testTopic);
		Card card4 = new Card("four", "4", testTopic);
		Card card5 = new Card("five", "5", testTopic);
		Card card6 = new Card("six", "6", testTopic);
		Card card7 = new Card("seven", "7", testTopic);
		Card card8 = new Card("eight", "8", testTopic);
		Card card9 = new Card("nine", "9", testTopic);
		Card card10 = new Card("ten", "10", testTopic);
		Card card11 = new Card("eleven", "10", testTopic);

		card0.setPriority(testUser, 1);
		card1.setPriority(testUser, 1);
		card2.setPriority(testUser, 1);
		card3.setPriority(testUser, 1);
		card4.setPriority(testUser, 2);
		card5.setPriority(testUser, 2);
		card6.setPriority(testUser, 2);
		card7.setPriority(testUser, 3);
		card8.setPriority(testUser, 3);
		card9.setPriority(testUser, 3);
		card10.setPriority(testUser, 3);
		card11.setPriority(testUser, 3);

		listCards1 = new ArrayList<Card>();
		listCards2 = new ArrayList<Card>();
		listCards3 = new ArrayList<Card>();
		listCards4 = new ArrayList<Card>();
		listCards5 = new ArrayList<Card>();
		listCards6 = new ArrayList<Card>();
		listCards7 = new ArrayList<Card>();
		listCards8 = new ArrayList<Card>();
		listCards9 = new ArrayList<Card>();
		listCards10 = new ArrayList<Card>();

		listCards1.add(card1);
		listCards1.add(card2);
		listCards1.add(card3);

		listCards2.add(card1);
		listCards2.add(card2);
		listCards2.add(card3);
		listCards2.add(card4);
		listCards2.add(card5);
		listCards2.add(card6);

		listCards3.add(card1);
		listCards3.add(card2);
		listCards3.add(card3);
		listCards3.add(card7);
		listCards3.add(card8);
		listCards3.add(card9);

		listCards4.add(card1);
		listCards4.add(card2);
		listCards4.add(card4);
		listCards4.add(card5);
		listCards4.add(card7);
		listCards4.add(card8);

		listCards5.add(card4);
		listCards5.add(card5);
		listCards5.add(card6);
		listCards5.add(card7);
		listCards5.add(card8);
		listCards5.add(card9);

		listCards6.add(card4);
		listCards6.add(card5);
		listCards6.add(card6);

		listCards7.add(card7);
		listCards7.add(card8);
		listCards7.add(card9);		
		
		listCards8.add(card7);
		listCards8.add(card8);
		listCards8.add(card9);
		listCards8.add(card10);
		listCards8.add(card11);
		
		listCards9.add(card1);
		listCards9.add(card2);
		listCards9.add(card3);
		listCards9.add(card4);
		
		listCards10.add(card0);
		listCards10.add(card1);
		listCards10.add(card2);
		listCards10.add(card3);
		listCards10.add(card7);

		return Arrays.asList(new Object[][] { 
				{ listCards1, 6 },
				{ listCards2, 15 },
				{ listCards3, 18 },
				{ listCards4, 18 },
				{ listCards5, 21 },
				{ listCards6, 9 },
				{ listCards7, 12 },
				{ listCards8, 20 },
				{ listCards9, 9 },
				{ listCards10, 12 },
			});
	}

	/**
	 * Tests if two identical cards are separated at least by two another cards.
	 * Tests if sequence (<code>orderList</code>) has valid size.
	 */
	@Test(timeout = 1000)
	public void testPrepareSequence() {

		testStudyService.setStudy(new Study());
		testStudyService.setDict(inputList);
		testStudyService.prepareSequence();
		testOrderList = testStudyService.getStudy().getOrderList();
		
		// In correct order list two identical cards should be separated at
		// least by two another cards
		boolean rightOrder = true;
		for (int i = 0; i < testOrderList.size() - 3; i++) {
			if (testOrderList.get(i).equals(testOrderList.get(i + 1))
					|| testOrderList.get(i).equals(testOrderList.get(i + 2))) {
				rightOrder = false;
			}
		}

		assertEquals(orderListSize, testOrderList.size());
		assertTrue(rightOrder);
	}

}

package model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * CardUnitTest class provides testing non-trivial methods of {@link Card} model
 * class.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public class CardUnitTest {

	private User testUser;
	private Card sample;

	/**
	 * Sets up necessary test objects.
	 */
	@Before
	public void setUp() {
		sample = new Card("word", "translation", "topic");
		testUser = new User("Test", "pass");
		testUser.setUserID(1L);
	}

	/**
	 * Tests {@link Card#getPriority(User)} method.
	 */
	@Test
	public void testGetPriority() {
		Integer priority = 1;

		Set<Long> prioritySet = new HashSet<Long>();
		prioritySet.add(testUser.getUserID());
		sample.setPriorityOne(prioritySet);

		assertEquals(priority, sample.getPriority(testUser));
	}

	/**
	 * Tests {@link Card#setPriority(User, Integer)} method. Checks if
	 * {@link IllegalArgumentException} was thrown with given illegal priority
	 * argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPriority() {
		Integer priority = 1;
		Integer illegalPriority = 0;
		sample.setPriority(testUser, priority);

		assertTrue(sample.getPriorityOne().contains(testUser.getUserID()));

		sample.setPriority(testUser, illegalPriority);
		assertTrue(sample.getPriorityOne().contains(testUser.getUserID()));
	}

	/**
	 * Tests {@link Card#incrementPriority(User)} method. Priority shouldn't
	 * change if it already has maximum value.
	 */
	@Test
	public void testIncrementPriority() {
		Set<Long> prioritySet = new HashSet<Long>();
		prioritySet.add(testUser.getUserID());
		sample.setPriorityOne(prioritySet);

		sample.incrementPriority(testUser);
		assertTrue(sample.getPriorityTwo().contains(testUser.getUserID()));

		sample.incrementPriority(testUser);
		assertTrue(sample.getPriorityThree().contains(testUser.getUserID()));

		sample.incrementPriority(testUser);
		assertTrue(sample.getPriorityThree().contains(testUser.getUserID()));
	}

	/**
	 * Tests {@link Card#decrementPriority(User)} method. Priority shouldn't
	 * change if it already has minimum value.
	 */
	@Test
	public void testDecrementPriority() {
		Set<Long> prioritySet = new HashSet<Long>();
		prioritySet.add(testUser.getUserID());
		sample.setPriorityThree(prioritySet);

		sample.decrementPriority(testUser);
		assertTrue(sample.getPriorityTwo().contains(testUser.getUserID()));

		sample.decrementPriority(testUser);
		assertTrue(sample.getPriorityOne().contains(testUser.getUserID()));

		sample.decrementPriority(testUser);
		assertTrue(sample.getPriorityOne().contains(testUser.getUserID()));
	}

}

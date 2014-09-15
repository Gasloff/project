package service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import db.DictDAO;
import db.UserDAO;

/**
 * UserServiceUnitTest provides testing {@link UserService} class.
 * 
 * @author Aleksandr Gaslov
 * 
 */
public class UserServiceUnitTest {

	private String testTopic;
	private List<Card> listCards;
	@Mock
	private UserDAO mockUserDAO;
	@Mock
	private DictDAO mockDictDAO;
	@InjectMocks
	private UserService testUserService;

	private static final int DEFAULT_PRIORITY = 2;

	/**
	 * Sets up necessary test objects and mocks.
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {

		testTopic = "topic";

		Card card1 = new Card("one", "1", testTopic);
		Card card2 = new Card("two", "2", testTopic);
		Card card3 = new Card("three", "3", testTopic);

		listCards = new ArrayList<Card>();

		listCards.add(card1);
		listCards.add(card2);
		listCards.add(card3);

		testUserService = new UserService();

		MockitoAnnotations.initMocks(this);
		Mockito.when(mockUserDAO.addUser((User) any())).thenReturn(-1L);
		Mockito.when(mockDictDAO.readDict((String) any()))
				.thenReturn(listCards);
		Mockito.doNothing().when(mockDictDAO).saveList((List<Card>) any());

	}

	/**
	 * Tests {@link UserService#createUser(String, String)} method. Checks if
	 * default priority set for new {@link User}
	 */
	@Test
	public void testCreateUser() {

		String login = "login";
		String password = "password";
		boolean rightPriority = true;

		// Priority for new User should be set to default
		User testUser = testUserService.createUser(login, password);
		for (int i = 0; i < listCards.size(); i++) {
			if (!listCards.get(i).getPriority(testUser)
					.equals(DEFAULT_PRIORITY)) {
				rightPriority = false;
			}
		}

		assertEquals(login, testUser.getLogin());
		assertEquals(password, testUser.getPassword());
		assertEquals(true, rightPriority);

	}

}

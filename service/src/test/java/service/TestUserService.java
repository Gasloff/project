package service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import db.DictDAO;
import db.UserDAO;

public class TestUserService {

	String testTopic;
	List<Card> listCards;
	UserService testUserService;
	
	private final int DEFAULT_PRIORITY = 2;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		
		testTopic = "topic";
		
		Card card1 = new Card("one", "1", testTopic);
		Card card2 = new Card("two", "2", testTopic);
		Card card3 = new Card("three", "3", testTopic);		
		
		listCards = new ArrayList<>();
		
		listCards.add(card1);
		listCards.add(card2);
		listCards.add(card3);		
		
		UserDAO mockUserDAO = mock(UserDAO.class);
		when(mockUserDAO.addUser((User) any())).thenReturn(-1L);
				
		DictDAO mockDictDAO = mock(DictDAO.class);
		when(mockDictDAO.readDict((String) any())).thenReturn(listCards);
		Mockito.doNothing().when(mockDictDAO).saveList((List<Card>) any());
		
		testUserService = new UserService();
		testUserService.setUserDao(mockUserDAO);
		testUserService.setDictDao(mockDictDAO);
	}
	
	@After
	public void tearDown() {
		testTopic = null;
		listCards = null;
		testUserService = null;
	}
	
	@Test
	public void testCreateUser() {
		
		String login = "login";
		String password = "password";
		boolean rightPriority = true;
		
		// Priority for new User should be set to default
		User testUser = testUserService.createUser(login, password);
		for (int i = 0; i < listCards.size(); i++) {
			if (!listCards.get(i).getPriority(testUser).equals(DEFAULT_PRIORITY)) {
				rightPriority = false;
			}
		}
		
		assertEquals(login, testUser.getLogin());
		assertEquals(password, testUser.getPassword());
		assertEquals(true, rightPriority);
		
	}
	
}

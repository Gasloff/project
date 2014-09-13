package web;

import java.util.List;

import model.Card;
import model.History;
import model.Study;
import model.User;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.HashCode;
import service.StudyService;
import service.UserService;

/**
 * Spring MVC controller class. Process all requests from client side.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Controller
public class AppController {

	@Autowired
	private UserService userService;
	@Autowired
	private StudyService studyService;

	private User user;
	private Card card;

	private final String CORRECT = "Correct";
	private final String NOT_CORRECT = "Not correct. Answer is: ";
	private final String OVER = "Study is over";
	private final String SAVED = "Study saved with ID: ";

	/**
	 * 
	 * @return create.jsp view
	 */
	@RequestMapping(value = "/create")
	public String create() {
		return "create";
	}

	/*
	 * returns service view
	 */
	@RequestMapping(value = "/populate")
	public String populate() {
		userService.populateDB();
		return "populated";
	}

	/**
	 * Returns main application view.
	 * 
	 * @return app.jsp view
	 */
	@RequestMapping(value = "/app")
	public String app() {
		return "app";
	}

	/**
	 * Returns if entered login exists in data storage. Creates and saves new
	 * {@link User} with given login and password if given login doesn't exist
	 * in data storage.
	 * 
	 * @param login
	 *            - entered login
	 * @param password
	 *            - entered password
	 * @return if entered login exists in data storage, entered login
	 */
	@RequestMapping(value = "/adduser", produces = "application/json")
	@ResponseBody
	public String adduser(@RequestParam("username") String login,
			@RequestParam("password") String password) {
		List<User> list = userService.getUserList();
		boolean exists = false;
		for (User u : list) {
			if (login.equals(u.getLogin())) {
				exists = true;
			}
		}
		if (!exists) {
			user = userService.createUser(login,
					HashCode.getHashPassword(password));
		}
		JSONObject jObj = new JSONObject();
		jObj.put("exists", exists);
		jObj.put("username", login);
		return jObj.toString();
	}

	/**
	 * Returns login of authorized user.
	 * 
	 * @return login of authorized user
	 */
	@RequestMapping(value = "/app/user/")
	@ResponseBody
	public String sendUserName() {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		user = userService.loadUser(userName);
		studyService.setUser(user);
		return user.getLogin();
	}

	/**
	 * Creates new Study with given topic and returns first cards word of new
	 * Study.
	 * 
	 * @param topic
	 *            - given topic
	 * @return word of first {@link Card} of created {@link Study}
	 */
	@RequestMapping(value = "/app/topic/")
	@ResponseBody
	public String newStudy(@RequestParam("topic") String topic) {
		studyService.createStudy(topic, user);
		card = studyService.nextCard();
		return card.getWord();
	}

	/**
	 * Returns list of available topics.
	 * 
	 * @return list of available topics
	 */
	@RequestMapping(value = "/app/listTopic/", produces = "application/json")
	@ResponseBody
	public List<String> listTopic() {
		return studyService.readTopicList();
	}

	/**
	 * Loads previously saved {@link Study} with given id and returns word of
	 * next {@link Card} of loaded Study
	 * 
	 * @param id
	 *            - given study id
	 * @return word of next {@link Card} of loaded {@link Study}
	 */
	@RequestMapping(value = "/app/loadStudy/")
	@ResponseBody
	public String loadStudy(@RequestParam("studyId") String id) {
		Long studyId = Long.parseLong(id);
		studyService.loadStudy(studyId);
		card = studyService.nextCard();
		return card.getWord();
	}

	/**
	 * Returns list of available saved {@link Study} objects for current user.
	 * JSON representation of mentioned list doesn't contain
	 * <code>history</code> and <code>user</code> fields.
	 * 
	 * @return list of saved {@link Study} objects for current user
	 */
	@RequestMapping(value = "/app/listStudy/", produces = "application/json")
	@ResponseBody
	public List<Study> listStudy() {
		return studyService.loadListByUser(user.getUserID());
	}

	/**
	 * Processes user's answer. Returns String claiming if answer is correct and
	 * correct translation, if it wasn't.
	 * 
	 * @param answer
	 *            - given user's answer
	 * @return String claiming if answer is correct and correct translation, if
	 *         it wasn't
	 */
	@RequestMapping(value = "/app/answer/", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String answer(@RequestParam("answer") String answer) {
		boolean correct = studyService.processAnswer(card, answer);
		String response;
		if (correct) {
			response = CORRECT;
		} else {
			response = NOT_CORRECT + card.getTranslation();
		}
		return response;
	}

	/**
	 * Returns word of next card or String claiming that study is over if there
	 * is no next card available.
	 * 
	 * @return word of next card or String claiming that study is over if there
	 *         is no next card available
	 */
	@RequestMapping(value = "/app/next/")
	@ResponseBody
	public String nextCard() {
		card = studyService.nextCard();
		String response;
		if (card != null) {
			response = card.getWord();
		} else {
			studyService.saveHistory();
			response = OVER;
		}
		return response;
	}

	/**
	 * Saves current study and returns id of saved study.
	 * 
	 * @return String containing id of saved Study.
	 */
	@RequestMapping(value = "/app/saveStudy/")
	@ResponseBody
	public String saveStudy() {
		return SAVED + studyService.saveStudy();
	}

	/**
	 * Returns list of {@link History} objects for current user. JSON
	 * representation of mentioned list doesn't contain <code>user</code> field.
	 * 
	 * @return list of {@link History} objects for current user.
	 */
	@RequestMapping(value = "/app/listHist/", produces = "application/json")
	@ResponseBody
	public List<History> listHist() {
		return userService.getHistList();
	}

}

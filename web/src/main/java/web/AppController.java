package web;

import java.util.List;

import model.Card;
import model.History;
import model.Study;
import model.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.HashCode;
import service.StudyService;
import service.UserService;

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
		
	@RequestMapping(value = "/create")
	public String create() {
		return "create";
	}
	
	@RequestMapping(value = "/populate")
	public String populate() {
		userService.populateDB();
		return "populated";
	}
	
	@RequestMapping(value = "/app")
	public String app() {
		return "app";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/adduser", produces="application/json")
	@ResponseBody public String adduser(@RequestParam("username") String login, @RequestParam("password") String password) {
		List <User> list = userService.getUserList();
		boolean exists = false;
		for (User u : list) {
			if (login.equals(u.getLogin())) {
				exists = true;
			}
		}
		if (!exists) {
			user = userService.createUser(login, HashCode.getHashPassword(password));
		}
		JSONObject jObj = new JSONObject();
		jObj.put("exists", exists);
		jObj.put("username", login);
		return jObj.toJSONString();
	}
	
	@RequestMapping(value = "/app/user/")
	@ResponseBody public String sendUserName() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.loadUser(userName);
		studyService.setUser(user);
		return user.getLogin();
	}
	
	@RequestMapping(value = "/app/topic/")
	@ResponseBody public String newStudy(@RequestParam("topic") String topic) {
		studyService.createStudy(topic, user);
		card = studyService.nextCard();
		return card.getWord();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/listTopic/", produces="application/json")
	@ResponseBody public String listTopic() {
		JSONArray jArray = new JSONArray();
		List<String> list = studyService.readTopicList();
		for (String topic : list) {
			JSONObject jObj = new JSONObject();
			jObj.put("topic", topic);
			jArray.add(jObj);
		}
		return jArray.toJSONString();
	}
	
	@RequestMapping(value = "/app/loadStudy/")
	@ResponseBody public String loadStudy(@RequestParam("studyId") String id) {
		Long studyId = Long.parseLong(id);
		studyService.loadStudy(studyId);
		card = studyService.nextCard();
		return card.getWord();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/listStudy/", produces="application/json")
	@ResponseBody public String listStudy() {
		JSONArray jArray = new JSONArray();
		List<Study> list = studyService.loadListByUser(user.getUserID());
		for (Study st : list) {
			JSONObject jObj = new JSONObject();
			jObj.put("id", st.getId());
			jObj.put("topic", st.getTopic());
			jObj.put("done", st.getPointer());
			jObj.put("remaining", (st.getOrderList().size() - st.getPointer()));
			jObj.put("date", st.getDate().toString());
			jArray.add(jObj);
		}
		return jArray.toJSONString();
	}
	
	@RequestMapping(value = "/app/answer/", produces = "text/plain;charset=UTF-8")
	@ResponseBody public String answer(@RequestParam("answer") String answer) {
		boolean correct = studyService.processAnswer(card, answer);
		String response;
		if (correct) {
			response = CORRECT;
		} else {
			response = NOT_CORRECT + card.getTranslation();
		}
		return response;
	}
	
	@RequestMapping(value = "/app/next/")
	@ResponseBody public String nextCard() {
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
	
	@RequestMapping(value = "/app/saveStudy/")
	@ResponseBody public String saveStudy() {
		return "Study saved with ID: " + studyService.saveStudy();		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/listHist/", produces="application/json")
	@ResponseBody public String listHist() {
		JSONArray jArray = new JSONArray();
		List<History> list = userService.getHistList();
		for (History hist : list) {
			JSONObject jObj = new JSONObject();
			jObj.put("id", hist.getHistID());
			jObj.put("answ", hist.getAnswered());
			jObj.put("corr", hist.getCorrect());
			jObj.put("histDate", hist.getDate().toString());
			jObj.put("topic", hist.getTopic());
			jArray.add(jObj);
		}
		return jArray.toJSONString();
	}
	
}

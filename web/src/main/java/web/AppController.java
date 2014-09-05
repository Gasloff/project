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

import service.StudyController;
import service.UserController;

@Controller
public class AppController {
	
	@Autowired	
	private UserController userController;
	@Autowired
	private StudyController studyController;
	
	private User user;
	private Card card;
	
	@RequestMapping(value = "/populate")
	public String populate() {
		userController.populateDB();
		return "populated";
	}
	
	@RequestMapping(value = "/app/user/")
	@ResponseBody public String sendUserName() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userController.loadUser(userName);
		studyController.setUser(user);
		return user.getLogin();
	}
	
	@RequestMapping(value = "/app/topic/")
	@ResponseBody public String newStudy(@RequestParam("topic") String topic) {
		studyController.createStudy(topic, user);
		card = studyController.nextCard();
		return card.getWord();
	}
	
	@RequestMapping(value = "/app/loadStudy/")
	@ResponseBody public String loadStudy(@RequestParam("studyId") String id) {
		Long studyId = Long.parseLong(id);
		studyController.loadStudy(studyId);
		card = studyController.nextCard();
		return card.getWord();
	}
	
	@RequestMapping(value = "/app/answer/", produces = "text/plain;charset=UTF-8")
	@ResponseBody public String answer(@RequestParam("answer") String answer) {
		boolean correct = studyController.processAnswer(card, answer);
		String response;
		if (correct) {
			response = "Correct";
		} else {
			response = "Not correct. Answer is: " + card.getTranslation();
		}
		return response;
	}
	
	@RequestMapping(value = "/app/next/")
	@ResponseBody public String nextCard() {
		card = studyController.nextCard();
		String response;
		if (card != null) {
			response = card.getWord();
		} else {
			studyController.saveHistory();
			response = "Study is over";
		}
		return response;
	}
	
	@RequestMapping(value = "/app/saveStudy/")
	@ResponseBody public String saveStudy() {
		return "Study saved with ID: " + studyController.saveStudy();		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/listStudy/", produces="application/json")
	@ResponseBody public String listStudy() {
		JSONArray jArray = new JSONArray();
		List<Study> list = studyController.loadListByUser(user.getUserID());
		for (Study st : list) {
			JSONObject jObj = new JSONObject();
			jObj.put("id", st.getId());
			jObj.put("topic", st.getTopic());
			jArray.add(jObj);
		}
		return jArray.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/app/listHist/", produces="application/json")
	@ResponseBody public String listHist() {
		JSONArray jArray = new JSONArray();
		List<History> list = userController.getHistList();
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
	
	@RequestMapping(value = "/app")
	public String app() {
		return "app";
	}
	
	@RequestMapping(value = "/create")
	public String create() {
		return "create";
	}
		
	@RequestMapping(value = "/adduser")
	public String adduser(@RequestParam("username") String login, @RequestParam("password") String password) {
		user = userController.createUser(login, password);
		return "added";
	}

}

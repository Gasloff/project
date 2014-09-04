package web;

import model.User;

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
	
	@RequestMapping(value = "/populate")
	public String populate() {
		userController.populateDB();
		return "populated";
	}
	
	@RequestMapping(value = "/app/topic/")
	@ResponseBody public String newStudy(@RequestParam("topic") String topic) {
		studyController.createStudy(topic, user);
		String response = "New study created with topic: " + studyController.getStudy().getTopic();
		//String response = user.getLogin() + ": " + user.getPassword();
		return response;
	}
	
	@RequestMapping(value = "/app/user/")
	@ResponseBody public String sendUserName() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userController.loadUser(userName);
		studyController.setUser(user);
		return user.getLogin();
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

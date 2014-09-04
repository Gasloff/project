package web;

import model.Study;
import model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.StudyController;
import service.UserController;

public class CopyOfAppController {

	private UserController userController;
	private StudyController studyController;

	@RequestMapping(value = "/populate")
	public String populate() {
		userController.populateDB();
		return "populated";
	}

	@RequestMapping(value = "/app")
	public String app() {
		return "app";
	}

	@RequestMapping(value = "/ajaxtest")
	public String atest() {
		return "ajaxtest";
	}

	@RequestMapping(value = "/ajax")
	@ResponseBody
	public String ajax() {
		return userController.loadUser("Aleksandr").getPassword();
	}

	@RequestMapping(value = "/auth")
	public String auth() {
		return "admin";
	}

	@RequestMapping(value = "/adduser")
	public void adduser(@RequestParam("username") String login,
			@RequestParam("password") String password) {
		userController.createUser(login, password);
	}

	@RequestMapping(value = "/user/{login}")
	public ModelAndView user(@PathVariable String login) {
		User user = userController.loadUser(login);
		ModelAndView model = new ModelAndView("test");
		model.addObject("password", user.getPassword());
		return model;
	}

	@RequestMapping(value = "/user")
	public ModelAndView aUser() {
		String login = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		User user = userController.loadUser(login);
		ModelAndView model = new ModelAndView("test");
		model.addObject("password", user.getPassword());
		return model;
	}

	@RequestMapping(value = "/study/{topic}")
	public ModelAndView study(@PathVariable String topic) {
		User user = userController.loadUser("Aleksandr");
		studyController.setUser(user);
		Study study = studyController.createStudy(topic, user);
		ModelAndView model = new ModelAndView("study");
		model.addObject("topic", study.getTopic());
		return model;
	}

}

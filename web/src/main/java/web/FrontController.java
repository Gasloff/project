package web;

import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import db.UserDAO;

@Controller
public class FrontController {

	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = "/{login}")
	public ModelAndView home(@PathVariable String login) {
		User user = userDao.getUser(login);
		ModelAndView model = new ModelAndView("test");
		model.addObject("password", user.getPassword());
		return model;
	}

}

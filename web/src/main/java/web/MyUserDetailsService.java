package web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import db.UserDAO;

/**
 * Provides loading user data by username and setting ROLE_USER to loaded user
 * 
 * @author Aleksandr Gaslov
 * 
 */
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	public MyUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		model.User user = userDao.loadUser(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return buildUserForAuthentication(user, authorities);
	}

	private User buildUserForAuthentication(model.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getLogin(), user.getPassword(), true, true, true,
				true, authorities);
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}

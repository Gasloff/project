package db;

import java.util.List;

import model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DbUserDAO class provides access to {@link User} entities in database
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Component
public class DbUserDAO implements UserDAO {

	private SessionFactory sessionFactory;
	
	private static final String USER_BY_LOGIN = "findUserByLogin";
	private static final String USER_BY_LOGIN_PARAM = "login";
	private static final String ALL_USERS = "allUsers";

	public DbUserDAO() {
	}

	/**
	 * Returns new DbUserDAO object with given Hibernate SessionFactory.
	 * 
	 * @param sessionFactory given Hibernate SessionFactory object
	 */
	@Autowired
	public DbUserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long addUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long userID = null;
		try {
			tx = session.beginTransaction();
			userID = (Long) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userID;
	}

	@Override
	public User loadUser(String login) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User) session.getNamedQuery(USER_BY_LOGIN)
					.setString(USER_BY_LOGIN_PARAM, login).uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();
			users = (List<User>) session.getNamedQuery(ALL_USERS).list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

}

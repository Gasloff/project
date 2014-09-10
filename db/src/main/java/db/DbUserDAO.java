package db;

import java.util.List;

import model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DbUserDAO implements UserDAO {

	private SessionFactory sessionFactory;

	public DbUserDAO() {}
	
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
			user = (User) session
					.getNamedQuery("findUserByLogin")
					.setString("login", login).uniqueResult();
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
			users = (List<User>) session
					.createQuery("FROM User u ORDER BY u.userID").list();
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

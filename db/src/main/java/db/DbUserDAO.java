package db;

import model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DbUserDAO implements UserDAO {

	private static SessionFactory factory;

	@Override
	public Long addUser(User user) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-hibernate.xml");
		factory = (SessionFactory) context.getBean("sessionFactory");
		Session session = factory.openSession();

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
		context.close();
		return userID;
	}

	@Override
	public User getUser(String login) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-hibernate.xml");
		factory = (SessionFactory) context.getBean("sessionFactory");
		Session session = factory.openSession();

		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User) session
					.createQuery("FROM User u WHERE u.login = :login")
					.setString("login", login).uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		context.close();
		return user;
	}

}

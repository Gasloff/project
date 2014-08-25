package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.User;

public class DbUserDAO implements UserDAO {

	private static SessionFactory factory;
	
	@Override
	public Long addUser(User user) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-hibernate.xml");

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

}

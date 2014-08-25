package db;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.History;

public class DbHistoryDAO implements HistoryDAO {

	private static SessionFactory factory;
	
	@Override
	public Long saveHistory(History history) throws IOException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-hibernate.xml");

	    factory = (SessionFactory) context.getBean("sessionFactory");
		
		Session session = factory.openSession();
		Transaction tx = null;
		Long histID = null;
		try {
			tx = session.beginTransaction();
			histID = (Long) session.save(history);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		context.close();
		return histID;
	}

}

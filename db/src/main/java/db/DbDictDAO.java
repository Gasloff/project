package db;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Card;

public class DbDictDAO implements DictDAO {

	private static SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Card> readDict(String topic) throws IOException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-hibernate.xml");
	    factory = (SessionFactory) context.getBean("sessionFactory");
	    Session session = factory.openSession();
		
	    Transaction tx = null;
		List<Card> dict = null;
		try {
			tx = session.beginTransaction();
			dict = (List<Card>) session.createQuery("FROM Card c WHERE c.topic = :topic ORDER BY c.id").setString("topic", topic).list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		context.close();
		return dict;
	}
	
	public Long addCard(Card card) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-hibernate.xml");
	    factory = (SessionFactory) context.getBean("sessionFactory");
		Session session = factory.openSession();
	
		Transaction tx = null;
		Long cardID = null;
		try {
			tx = session.beginTransaction();
			cardID = (Long) session.save(card);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		context.close();
		return cardID;
	}

}

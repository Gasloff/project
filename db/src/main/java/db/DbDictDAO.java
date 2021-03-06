package db;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Card;

/**
 * DbDictDAO class provides access to {@link Card} entities in database
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Component
public class DbDictDAO implements DictDAO {

	private SessionFactory sessionFactory;
	
	private static final String ALL = "all";
	private static final long DEFAULT_ID = -1L;
	private static final String ALL_CARDS = "allCards";
	private static final String CARDS_BY_TOPIC = "findCardsByTopic";
	private static final String CARDS_BY_TOPIC_PARAM = "topic";
	private static final String ALL_TOPICS = "allTopics";
	
	public DbDictDAO() {
	}

	/**
	 * Returns new DbDictDAO object with given Hibernate SessionFactory.
	 * 
	 * @param sessionFactory given Hibernate SessionFactory object
	 */
	@Autowired
	public DbDictDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Card> readDict(String topic) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Card> dict = null;
		try {
			tx = session.beginTransaction();
			if (topic.equals(ALL)) {
				dict = (List<Card>) session.getNamedQuery(ALL_CARDS).list();
			} else {
				dict = (List<Card>) session.getNamedQuery(CARDS_BY_TOPIC)
						.setString(CARDS_BY_TOPIC_PARAM, topic).list();
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return dict;
	}

	@Override
	public Long saveCard(Card card) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long cardID = card.getId();

		if (cardID.equals(DEFAULT_ID)) {
			try {
				tx = session.beginTransaction();
				savedID = (Long) session.save(card);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			try {
				tx = session.beginTransaction();
				session.update(card);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			savedID = cardID;
		}

		return savedID;
	}

	@Override
	public void saveList(List<Card> list) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Card card : list) {
				session.update(card);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> readTopicList() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<String> list = null;
		try {
			tx = session.beginTransaction();
			list = (List<String>) session.getNamedQuery(ALL_TOPICS).list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}

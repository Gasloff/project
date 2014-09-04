package db;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Card;

public class DbDictDAO implements DictDAO {

	private SessionFactory sessionFactory;

	public DbDictDAO() {
	}

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
			if (topic.equals("all")) {
				dict = (List<Card>) session.createQuery(
						"FROM Card c ORDER BY c.id").list();
			} else {
				dict = (List<Card>) session
						.createQuery(
								"FROM Card c WHERE c.topic = :topic ORDER BY c.id")
						.setString("topic", topic).list();
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
	public Long addCard(Card card) {
		Session session = sessionFactory.openSession();
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

		return cardID;
	}

	@Override
	public Long saveCard(Card card) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long cardID = card.getId();

		if (cardID.equals(-1L)) {
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
}

package db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.History;

public class DbHistoryDAO implements HistoryDAO {

	private SessionFactory sessionFactory;

	public DbHistoryDAO() {}
	
	public DbHistoryDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long saveHistory(History history) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long histID = history.getHistID();

		if (histID.equals(-1L)) {
			try {
				tx = session.beginTransaction();
				savedID = (Long) session.save(history);
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
				session.update(history);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			savedID = histID;
		}

		return savedID;
	}

	public History readHistory(Long histID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		History history = null;
		try {
			tx = session.beginTransaction();
			history = (History) session.get(History.class, histID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return history;
	}

	@SuppressWarnings("unchecked")
	public List<History> getListByUser(Long userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<History> list = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			list = (List<History>) session.getNamedQuery("allHistoryByUser")
					.setLong("userId", userId).list();
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

package db;

import java.util.ArrayList;
import java.util.List;

import model.Study;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DbStudyDAO implements StudyDAO {

	private SessionFactory sessionFactory;

	public DbStudyDAO() {}
	
	public DbStudyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long saveStudy(Study study) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long studyID = study.getId();

		if (studyID.equals(-1L)) {
			try {
				tx = session.beginTransaction();
				savedID = (Long) session.save(study);
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
				session.update(study);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			savedID = studyID;
		}

		return savedID;
	}

	@Override
	public Study readStudy(Long studyID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Study study = null;
		try {
			tx = session.beginTransaction();
			study = (Study) session.get(Study.class, studyID);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return study;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> getListByUser(Long userId) {
		List<Study> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list = (List<Study>) session.createQuery("from Study s where s.user.userID = :id")
					.setLong("id", userId).list();
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

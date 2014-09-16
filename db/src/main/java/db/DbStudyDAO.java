package db;

import java.util.ArrayList;
import java.util.List;

import model.Study;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DbStudyDAO class provides access to {@link Study} entities in database.
 * 
 * @author Aleksandr Gaslov
 * 
 */
@Component
public class DbStudyDAO implements StudyDAO {

	private SessionFactory sessionFactory;

	private static final long DEFAULT_ID = -1L;
	private static final String ALL_STUDY_BY_USER = "allStudyByUser";
	private static final String USER_ID_PARAM = "userId";
	
	public DbStudyDAO() {
	}

	/**
	 * Returns new DbStudyDAO object with given Hibernate SessionFactory.
	 * 
	 * @param sessionFactory given Hibernate SessionFactory object
	 */
	@Autowired
	public DbStudyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long saveStudy(Study study) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long studyID = study.getId();

		if (studyID.equals(DEFAULT_ID)) {
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
	public void deleteStudy(Study study) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long studyID = study.getId();

		if (!studyID.equals(DEFAULT_ID)) {
			try {
				tx = session.beginTransaction();
				session.delete(study);
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
		List<Study> list = new ArrayList<Study>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list = (List<Study>) session.getNamedQuery(ALL_STUDY_BY_USER)
					.setLong(USER_ID_PARAM, userId).list();
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

package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Study;

public class DbStudyDAO implements StudyDAO {

	private static SessionFactory factory;
	private Session session;

	public DbStudyDAO() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-hibernate.xml");
		factory = (SessionFactory) context.getBean("sessionFactory");
		context.close();
	}

	@Override
	public Long saveStudy(Study study) {
		session = factory.openSession();
		Transaction tx = null;
		Long savedID = null;
		Long studyID = study.getId();

		if (studyID == -1L) {
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
		session = factory.openSession();
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

}

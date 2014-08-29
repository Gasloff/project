package db;

import model.Study;

public interface StudyDAO {

	Long saveStudy(Study study);

	Study readStudy(Long studyID);

}

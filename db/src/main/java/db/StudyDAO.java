package db;

import java.util.List;

import model.Study;

public interface StudyDAO {

	Long saveStudy(Study study);
	
	void deleteStudy(Study study);

	Study readStudy(Long studyID);
	
	List<Study> getListByUser(Long userId);

}

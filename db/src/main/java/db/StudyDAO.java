package db;

import java.io.IOException;
import model.Study;

public interface StudyDAO {

	Long saveStudy(Study study) throws IOException;

	Study readStudy(Long studyID) throws IOException;

}

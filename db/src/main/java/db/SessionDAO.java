package db;

import java.io.IOException;
import java.util.List;

import model.Study;

public interface SessionDAO {
	
	void saveStudy(Study study) throws IOException;
	
	Study readStudy(String studyID) throws IOException;
	
	List<Integer> readOrderList(int oListID) throws IOException;
	
}
package db;

import java.io.IOException;

import model.History;

public interface HistoryDAO {

	int getNewID() throws IOException;
	
	void saveHistory(History history) throws IOException;
}

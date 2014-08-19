package db;

import java.io.IOException;
import java.util.List;

import model.Session;

public interface SessionDAO {
	
	void saveSession(Session session) throws IOException;
	
	Session readSession(String sessionID) throws IOException;
	
	List<Integer> readOrderList(int oListID) throws IOException;
	
}

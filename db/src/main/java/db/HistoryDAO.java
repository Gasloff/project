package db;

import java.util.List;

import model.History;

public interface HistoryDAO {

	Long saveHistory(History history);
	
	History readHistory(Long histID);
	
	List<History> getListByUser(Long userId);
}

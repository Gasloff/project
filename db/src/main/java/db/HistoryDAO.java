package db;

import java.io.IOException;

import model.History;

public interface HistoryDAO {

	Long saveHistory(History history) throws IOException;
}

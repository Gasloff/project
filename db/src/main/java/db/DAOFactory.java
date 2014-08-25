package db;

public interface DAOFactory {
	
	public DictDAO createDictDAO();
	
	public HistoryDAO createHistoryDAO();
	
	public StudyDAO createStudyDAO();
	
}

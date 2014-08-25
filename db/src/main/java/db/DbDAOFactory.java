package db;

public class DbDAOFactory implements DAOFactory {

	@Override
	public DictDAO createDictDAO() {
		return new DbDictDAO();
	}

	@Override
	public HistoryDAO createHistoryDAO() {
		return new DbHistoryDAO();
	}
	
	@Override
	public StudyDAO createStudyDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}

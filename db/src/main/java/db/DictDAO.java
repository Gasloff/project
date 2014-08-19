package db;

import java.io.IOException;
import java.util.List;

import model.Card;

public interface DictDAO {

	List<Card> readDict(String topic) throws IOException; 
	
}

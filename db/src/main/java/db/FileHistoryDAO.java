package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.History;

public class FileHistoryDAO implements HistoryDAO {

	private String FILENAME = "D:\\history.txt";

	
	public int getNewID() throws IOException {
		int newID = 0;
		List<Integer> list = new ArrayList<Integer>();

		FileReader fileReader = new FileReader(FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			list.add((int) streamTokenizer.nval);
			while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL)
				;
		}
		if (list.size() != 0) {
			Collections.sort(list);
			newID = list.get(list.size() - 1) + 1;
		}
		return newID;
	}

	@Override
	public Long saveHistory(History history) throws IOException {
		FileWriter fileWriter = new FileWriter(FILENAME, true);
		fileWriter.write(history.getHistID() + " " + history.getAnswered() + " "
				+ history.getCorrect() + System.getProperty("line.separator"));
		fileWriter.close();
		return 0L;
	}

}

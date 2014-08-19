package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import model.Card;

public class FileDictDAO implements DictDAO {
	
	private String FILENAME = "D:\\dict.txt";
	
	@Override
	public List<Card> readDict(String dictTopic) throws IOException {
		
		List<Card> dict = new ArrayList<>();
		int cardCounter = 0;
		
		FileReader fileReader = new FileReader(FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
				
		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
				String word = streamTokenizer.sval;
				streamTokenizer.nextToken();
				String translation = streamTokenizer.sval;
				streamTokenizer.nextToken();
				String topic = streamTokenizer.sval;
				streamTokenizer.nextToken();
				int priority = (int) streamTokenizer.nval;
				
				Card card = new Card(cardCounter++, word, translation, topic, priority);
				if (dictTopic.equals("all")) {
					dict.add(card);
				} else if (topic.equals(dictTopic)) {
					dict.add(card);
				}
		}
						
		return dict;
	}

}

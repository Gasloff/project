package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.History;
import model.Study;
import model.User;

/**
 * Doesn't work with modified according to hibernate model classes
 * 
 * @author User
 *
 */

public class FileSessionDAO implements SessionDAO {

	private String S_FILENAME = "D:\\sessions.txt";
	private String OL_FILENAME = "D:\\olists.txt";

	@Override
	public void saveStudy(Study session) throws IOException {
		List<Study> studies = new ArrayList<Study>();

		List<String> list = getSessionsList();
		for (String s : list) {
			studies.add(readStudy(s));
		}

		int index = -1;
		boolean exists = false;
		for (Study s : studies) {
			if (s.getId().equals(session.getId())) {
				index = studies.indexOf(s);
				exists = true;
			}
		}
		if (exists){
			studies.set(index, session);
		} else {
			studies.add(session);
		}
		
		FileWriter sCleaner = new FileWriter(S_FILENAME);
		sCleaner.close();
		
		FileWriter sFileWriter = new FileWriter(S_FILENAME, true);
		
		for (Study s : studies) {
			writeSession(sFileWriter, s);
		}
		sFileWriter.close();
	}

	@SuppressWarnings("unused")
	@Override
	public Study readStudy(String sessionID) throws IOException {
		FileReader fileReader = new FileReader(S_FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		String id = new String();
		String topic = new String();
		int oListID = -1;
		int pointer = -1;
		int histID = -1;

		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			if (streamTokenizer.sval.equals(sessionID)) {
				id = streamTokenizer.sval;
				streamTokenizer.nextToken();
				topic = streamTokenizer.sval;
				streamTokenizer.nextToken();
				oListID = (int) streamTokenizer.nval;
				streamTokenizer.nextToken();
				pointer = (int) streamTokenizer.nval;
				streamTokenizer.nextToken();
				histID = (int) streamTokenizer.nval;
				break;
			} else {
				while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL)
					;
			}
		}

		//Need to get oList and hist somehow using oListID and histID
		List<Integer> oList = null; 
		History hist = null;
		
		Study study = new Study(topic, new User());
		study.setOrderList(oList);
		study.setOrderList(readOrderList(oListID));
		study.setPointer(pointer);
		study.setHistory(hist);

		return study;
	}

	@Override
	public List<Integer> readOrderList(int oListID) throws IOException {
		FileReader fileReader = new FileReader(OL_FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		List<Integer> orderList = new ArrayList<Integer>();
		int i;

		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			if (streamTokenizer.nval == oListID) {
				while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL) {
					i = (int) streamTokenizer.nval;
					orderList.add(i);
				}
			} else {
				while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL)
					;
			}

		}

		return orderList;
	}

	public List<String> getSessionsList() throws IOException {
		List<String> list = new ArrayList<>();

		FileReader fileReader = new FileReader(S_FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			list.add(streamTokenizer.sval);
			while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL)
				;
		}

		return list;
	}

	@SuppressWarnings("unused")
	private void writeSession(FileWriter sFileWriter, Study study)
			throws IOException {
		
		PrintWriter pw = new PrintWriter(sFileWriter);
		
		int oListID = -1;
		List<Integer> oList = null;
		
		if (study.getOrderList() != null) {
			oList = study.getOrderList();
		} else {
			List<Integer> oListIDs = getOListIDs();
			oListID = oListIDs.get(oListIDs.size() - 1) + 1;
			saveOrderList(oListID, study.getOrderList());
		}
		
		int histID = -1;
		History hist = null;
		
		if (study.getHistory() != null) {
			hist = study.getHistory();
		} else {
			histID = -1;
		}
		
		String sessionString = study.getId() + " " + study.getTopic() + " "
				+ oListID + " " + study.getPointer() + " " + histID;
		pw.println(sessionString);

	}

	private void saveOrderList(int oListID, List<Integer> oList) throws IOException {
		FileWriter olFileWriter = new FileWriter(OL_FILENAME, true);
		
		StringBuffer sB = new StringBuffer();
		sB.append(oListID + " ");
		for (Integer i : oList) {
			sB.append(i + " ");
		}
		sB.append('\n');
		olFileWriter.write(sB.toString());
		
		olFileWriter.close();
	}

	private List<Integer> getOListIDs() throws IOException {
		List<Integer> list = new ArrayList<>();

		FileReader fileReader = new FileReader(OL_FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			list.add((int) streamTokenizer.nval);
			while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOL)
				;
		}
		Collections.sort(list);
		return list;
	}
	
}

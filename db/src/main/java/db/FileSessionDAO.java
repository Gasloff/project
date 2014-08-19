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

import model.Session;

public class FileSessionDAO implements SessionDAO {

	private String S_FILENAME = "D:\\sessions.txt";
	private String OL_FILENAME = "D:\\olists.txt";

	@Override
	public void saveSession(Session session) throws IOException {
		List<Session> sessions = new ArrayList<>();

		List<String> list = getSessionsList();
		for (String s : list) {
			sessions.add(readSession(s));
		}

		int index = -1;
		boolean exists = false;
		for (Session s : sessions) {
			if (s.getId().equals(session.getId())) {
				index = sessions.indexOf(s);
				exists = true;
			}
		}
		if (exists){
			sessions.set(index, session);
		} else {
			sessions.add(session);
		}
		
		FileWriter sCleaner = new FileWriter(S_FILENAME);
		sCleaner.close();
		
		FileWriter sFileWriter = new FileWriter(S_FILENAME, true);
		
		for (Session s : sessions) {
			writeSession(sFileWriter, s);
		}
		sFileWriter.close();
	}

	@Override
	public Session readSession(String sessionID) throws IOException {
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

		Session session = new Session(id, topic);
		session.setOrderListID(oListID);
		session.setOrderList(readOrderList(oListID));
		session.setPointer(pointer);
		session.setHistoryID(histID);

		return session;
	}

	@Override
	public List<Integer> readOrderList(int oListID) throws IOException {
		FileReader fileReader = new FileReader(OL_FILENAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		streamTokenizer.eolIsSignificant(true);

		List<Integer> orderList = new ArrayList<>();
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

	private void writeSession(FileWriter sFileWriter, Session session)
			throws IOException {
		
		PrintWriter pw = new PrintWriter(sFileWriter);
		
		int oListID;
		if (session.getOrderListID() != -1) {
			oListID = session.getOrderListID();
		} else {
			List<Integer> oListIDs = getOListIDs();
			oListID = oListIDs.get(oListIDs.size() - 1) + 1;
			saveOrderList(oListID, session.getOrderList());
		}
		
		int histID;
		if (session.getHistoryID() != -1) {
			histID = session.getHistoryID();
		} else {
			histID = -1;
		}
		
		String sessionString = session.getId() + " " + session.getTopic() + " "
				+ oListID + " " + session.getPointer() + " " + histID;
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

package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleStudyInterface implements StudyInterface {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public Long obtainStudyID() {
		Long id = null;
		System.out.println("Please enter unique session ID.");
		try {
			id = Long.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public String obtainTopic() {
		String topic = new String();
		System.out.println("Please enter topic. \"all\" for all words in database.");
		try {
			topic = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return topic;
	}

	@Override
	public Long obtainSavedStudyID() {
		Long id = null;
		System.out.println("Please enter saved session ID.");
		try {
			id = Long.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

}

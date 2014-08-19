package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleSessionInterface implements SessionInterface {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public String obtainSessionID() {
		String id = new String();
		System.out.println("Please enter unique session ID.");
		try {
			id = br.readLine();
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
	public String obtainSavedSessionID() {
		String id = new String();
		System.out.println("Please enter saved session ID.");
		try {
			id = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

}

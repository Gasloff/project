package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Card;
import model.User;

public class ConsoleCardController implements CardController {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	@Override
	public int showCard(Card card, User user) {
		int response = -1;
				
		System.out.println("Word: " + card.getWord());
		System.out.println("Enter translation");
				
		String answer = "";
		try {
			answer = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (answer.equals(card.getTranslation())) {
			response = 1;
			System.out.println("Correct!");
		} else if (answer.equals("save")) {
			response = 2;
		} else {
			System.out.println("Not correct!");
			int priority = card.getPriority(user);
			if (priority < 3) {
				card.incrementPriority(user);
			}
			response = 0;
		}
		return response;
	}

}

package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Card;
import model.User;

public class ConsoleCardController implements CardController {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	@Override
	public boolean showCard(Card card, User user) {
		boolean correct = false;
				
		System.out.println("Word: " + card.getWord());
		System.out.println("Enter translation");
				
		String answer = "";
		try {
			answer = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (card.getTranslation().equals(answer)) {
			correct = true;
			System.out.println("Correct!");
		} else {
			System.out.println("Not correct!");
			int priority = card.getPriority(user);
			if (priority < 3) {
				card.incrementPriority(user);
			}
		}
		return correct;
	}

}

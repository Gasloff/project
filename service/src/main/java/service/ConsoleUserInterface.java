package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUserInterface implements UserInterface {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public boolean existingUser() {
		System.out.println("Do you want create new user account? y/n.");
		String answer = new String();
		try {
			answer = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (answer) {
		case "y":
			return false;
		case "n":
			return true;
		default:
			System.out.println("Illegal argument");
			return existingUser();
		}
	}
	
	@Override
	public boolean savedStudy() {
		System.out.println("Do you want to resume saved session? y/n.");
		String answer = new String();
		try {
			answer = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (answer) {
		case "y":
			return true;
		case "n":
			return false;
		default:
			System.out.println("Illegal argument");
			return savedStudy();
		}
	}

	@Override
	public String obtainLogin() {
		String login = new String();
		System.out.println("Please enter existing user login.");
		try {
			login = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return login;
	}

	@Override
	public String obtainPassword() {
		String password = new String();
		System.out.println("Please enter password.");
		try {
			password = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return password;
	}

	@Override
	public String obtainNewLogin() {
		String login = new String();
		System.out.println("Please unique new user login.");
		try {
			login = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return login;
	}

	@Override
	public String obtainNewPassword() {
		String password = new String();
		System.out.println("Please enter new user password.");
		try {
			password = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return password;
	}

}

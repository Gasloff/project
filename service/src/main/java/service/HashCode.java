package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashCode {

	public static String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
}

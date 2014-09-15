package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * HashCode class provides hashing for passwords
 * 
 * @author Aleksandr Gaslov
 * 
 */
public class HashCode {

	/**
	 * Returns hash value for given password.
	 * 
	 * @param password
	 * @return hashed password
	 */
	public static String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
}

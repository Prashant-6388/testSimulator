package com.pc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pc.model.User;

/**
 * @author Prashant
 *
 */
public class UserUtils {

	@Autowired
	private static PasswordEncoder passwordEncoder;
	
	/**
	 * create a user with basic info
	 * @return User
	 */
	public static User createBasicUser() {
		User user = new User();
		user.setUsername("prashant");
		String password = passwordEncoder.encode("prashant");
		user.setPassword(password);
		user.setActive(true);
		user.setEmail("prashant.6388@gmail.com");
		return user;
	}
}

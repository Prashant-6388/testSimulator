package com.pc.utils;

import com.pc.model.User;

/**
 * @author Prashant
 *
 */
public class UserUtils {

	/**
	 * create a user with basic info
	 * @return User
	 */
	public static User createBasicUser() {
		User user = new User();
		user.setUsername("prashant");
		user.setPassword("prashant");
		user.setActive(true);
		user.setEmail("prashant.6388@gmail.com");
		return user;
	}
}

package com.pc.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pc.controller.ForgotMyPasswordController;
import com.pc.model.User;
import com.pc.model.frontend.BasicAccountPayload;

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
	
	public static String createPasswordResetURL(HttpServletRequest servletRequest, long userId, String token)
	{
		 String passwordResetURL = servletRequest.getScheme() 
				 			+ ":///"
				 			+ servletRequest.getServerName()
				 			+ ":"
				 			+ servletRequest.getServerPort()
				 			+ servletRequest.getContextPath()
				 			+ ForgotMyPasswordController.RESET_PASSWORD_URL
				 			+ "?id="
				 			+ userId
				 			+ "&token="
				 			+ token;
				 			
		 return passwordResetURL;
	}
	
	public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T frontendPayload) {
        User user = new User();
        user.setUsername(frontendPayload.getUsername());
        user.setPassword(frontendPayload.getPassword());
        user.setFirstName(frontendPayload.getFirstName());
        user.setLastName(frontendPayload.getLastName());
        user.setEmail(frontendPayload.getEmail());
        user.setPhoneNumber(frontendPayload.getPhoneNumber());
        user.setCountry(frontendPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(frontendPayload.getDescription());

        return user;
    }
}

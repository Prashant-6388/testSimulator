package com.pc.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	public UserDetails loadUserDetailsByUserName(String username);
	/*public User creatUser(User user, Plans plan)
	{
		
	}*/
}

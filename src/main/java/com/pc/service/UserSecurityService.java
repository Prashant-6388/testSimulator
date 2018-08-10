package com.pc.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pc.service.UserService;
import com.pc.model.User;
import com.pc.persistance.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{

	public static final Logger log = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user==null) {
			log.warn("user {} not found",username);
			throw new UsernameNotFoundException("User "+username+" not found");
		}
		return user;
	}

}

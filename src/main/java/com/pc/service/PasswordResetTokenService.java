package com.pc.service;


import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.model.PasswordResetToken;
import com.pc.model.User;
import com.pc.persistance.PasswordRestTokenRepository;
import com.pc.persistance.UserRepository;

@Service
public class PasswordResetTokenService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordRestTokenRepository passwordResetTokenRepository;
	
	public static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);
	
	@Value("${token.expiration.length.minute}")
	private int expirationTime;
	
	/**
	 * @param token
	 * @return
	 */
	public PasswordResetToken findByToken (String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	
	/**
	 * @param email
	 * @return
	 */
	@Transactional
	public PasswordResetToken createPasswordResetTokenForEmail(String email) {
		PasswordResetToken resetToken=null;
		User user = userRepository.findByEmail(email);
		if(user != null) {
			String token = UUID.randomUUID().toString();
			LocalDateTime now = LocalDateTime.now();
			resetToken = new PasswordResetToken(token, user, now, expirationTime);
			resetToken = passwordResetTokenRepository.save(resetToken);
			LOG.debug("Successfully created token {} for user {}", token, user.getUsername());
		}
		else {
			LOG.debug("User not found for email : "+email);
		}
		return resetToken;
	}
}

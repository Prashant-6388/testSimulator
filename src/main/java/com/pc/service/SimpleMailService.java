package com.pc.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailService {

	@Autowired
	private JavaMailSender sender;

	public static final Logger LOG = LoggerFactory.getLogger(SimpleMailService.class);
	
	public boolean sendMail(String email, String resetURL) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(email);
			helper.setText("Greetings :)"
					+ "click here to reset passwrd: "+resetURL);
			helper.setSubject("Mail From Spring Boot");
			sender.send(message);
			return true;
		} catch (MessagingException e) {
			LOG.debug("Error sending mail", e.getCause());
			return false;
			
		}
	}
}

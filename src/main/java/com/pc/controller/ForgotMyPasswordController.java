package com.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pc.model.PasswordResetToken;
import com.pc.service.PasswordResetTokenService;
import com.pc.service.SimpleMailService;
import com.pc.utils.UserUtils;

@Controller
public class ForgotMyPasswordController {

	public static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
	
	public static final String EMAIL_ADDRESS_VIEW_NAME = "forgotPassword/emailForm";
	
	public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgotPassword";
	
	public static final String RESET_PASSWORD_URL = "/resetPassword";
	
	@Autowired
	PasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	SimpleMailService simpleMailService; 
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.GET)
	public String forgotPasswordGet(HttpServletRequest request,@RequestParam("email") String email, ModelMap map) {
		email="prashant.6388@gmail.com";
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);
		String resetURL = UserUtils.createPasswordResetURL(request, passwordResetToken.getUser().getId(), passwordResetToken.getToken());
		boolean isSentSuccessfully = simpleMailService.sendMail(email, resetURL);
		if(isSentSuccessfully == true) {
			LOG.info("Token value : {}", passwordResetToken.getToken());
		}
		else {
			LOG.debug("Email sending failed");
		}
		return EMAIL_ADDRESS_VIEW_NAME;
	}
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method = RequestMethod.POST)
	public String forgotPasswordPost(HttpServletRequest request,@RequestParam("email") String email, ModelMap map) {
		/*email="prashant.6388@gmail.com";
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);
		if(passwordResetToken == null){
			LOG.warn("Couldn't find token for email {}", email);
		} else {
			LOG.info("Token value : {}", passwordResetToken.getToken());
			simpleMailService.sendMail(email, );
		}
		return "";*/
		return "";
	}
}

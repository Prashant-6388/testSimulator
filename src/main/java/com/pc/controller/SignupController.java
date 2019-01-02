package com.pc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pc.enums.PlansEnum;
import com.pc.enums.RolesEnum;
import com.pc.model.Plan;
import com.pc.model.Role;
import com.pc.model.User;
import com.pc.model.UserRole;
import com.pc.model.frontend.BasicAccountPayload;
import com.pc.model.frontend.ProAccountPayload;
import com.pc.service.PlanService;
import com.pc.service.S3Service;
import com.pc.service.UserService;
import com.pc.utils.UserUtils;

@Controller
public class SignupController {

	public static final Logger log = LoggerFactory.getLogger(SignupController.class);

	public static final String SIGNUP_URL = "/signup";

	public static final String PAYLOAD_MODEL_KEY_NAME = "payload";

	public static final String SIGNUP_PAGE = "/signup";

	public static final String SUBSCRIPTION_VIEW_NAME = "/signup";

	private static final String SIGNEDUP_MESSAGE_KEY = "signedUp";

	private static final String ERROR_MESSAGE_KEY = "error";

	private static final String DUPLICATE_USERNAME_KEY = "duplicatedUsername";

	private static final String DUPLICATE_EMAIL_KEY = "duplicatedEmail";

	@Autowired
	UserService userService;

	@Autowired
	PlanService planService;
	
	@Autowired
	S3Service s3service;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupGet(@RequestParam("planId") int planId, ModelMap model) {
		log.debug("user signup page invoked");

		if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.ADVANCED.getId())
			throw new IllegalArgumentException("Plan is not valid");

		model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());
		return SIGNUP_PAGE;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@RequestParam(name = "planId", required = true) int planId,
			@RequestParam(name = "file", required = false) MultipartFile file,
			@ModelAttribute(PAYLOAD_MODEL_KEY_NAME) @Valid ProAccountPayload payLoad, ModelMap model)
			throws IOException {
  		log.debug("user signup page invoked for saving user using post method");

		if (planId != PlansEnum.BASIC.getId() && planId != PlansEnum.ADVANCED.getId()) {
			model.addAttribute(SIGNEDUP_MESSAGE_KEY, "false");
			model.addAttribute(ERROR_MESSAGE_KEY, "Plan not found");
			return SUBSCRIPTION_VIEW_NAME;
		}

		checkForDuplicates(payLoad, model);

		boolean duplicates = false;

		List<String> errorMessages = new ArrayList<>();
		if (model.containsAttribute(DUPLICATE_USERNAME_KEY)) {
			log.warn("Username already exists");
			model.addAttribute(SIGNEDUP_MESSAGE_KEY, "false");
			errorMessages.add("Username already exists");
			duplicates = true;
		}
		if (model.containsAttribute(DUPLICATE_EMAIL_KEY)) {
			log.warn("Email already exists");
			model.addAttribute(SIGNEDUP_MESSAGE_KEY, "false");
			errorMessages.add("Email already exists");
			duplicates = true;
		}

		if (duplicates) {
			model.addAttribute(ERROR_MESSAGE_KEY, errorMessages);
			return SUBSCRIPTION_VIEW_NAME;
		}

		log.debug("transforming userpayrole into user");
		User user = UserUtils.fromWebUserToDomainUser(payLoad);

		//for uploading file to amazon  s3
		if(file!=null && !file.isEmpty()){
		 	String profileImageURL = s3service.storeProfileImage(file, payLoad.getUsername());
		 	if(profileImageURL != null) {
		 		user.setProfileImageUrl(profileImageURL);
		 	}
		 	else {
		 		log.warn("there is problem uploading image to amazon s3, user profile {} will be creatd with out profile image",user.getUsername());
		 	}
		}
		
		
		
		Plan selectedPlan = planService.findPlanById(planId);
		if (selectedPlan == null) {
			log.debug("The plan id {} could not be found", planId);
			model.addAttribute(SIGNEDUP_MESSAGE_KEY, "false");
			model.addAttribute(ERROR_MESSAGE_KEY, "plan not found");
			return SUBSCRIPTION_VIEW_NAME;
		}
		
		user.setPlan(selectedPlan);
		
		User registeredUser = null;
		
		Set<UserRole> roles = new HashSet<>();
		if(planId == PlansEnum.BASIC.getId()) {
			roles.add(new UserRole(user,new Role(RolesEnum.BASIC)));
			registeredUser = userService.createUser(user, PlansEnum.BASIC, roles);
			log.debug(payLoad.toString());
		}
		else {
			roles.add(new UserRole(user,new Role(RolesEnum.PRO)));
			registeredUser = userService.createUser(user, PlansEnum.ADVANCED, roles);
			log.debug(payLoad.toString());
		}
		
		//Auto login user
		Authentication auth = new UsernamePasswordAuthenticationToken(registeredUser, null,registeredUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		log.info("user created successfully");
		
		model.addAttribute(SIGNEDUP_MESSAGE_KEY,"true");
		
		return SIGNUP_PAGE;
	}

	private void checkForDuplicates(BasicAccountPayload payLoad, ModelMap model) {
		// check for username
		if (userService.findUserByUsername(payLoad.getUsername()) != null) {
			model.addAttribute(DUPLICATE_USERNAME_KEY, true);
		}
		if (userService.findUserbyEmail(payLoad.getEmail()) != null) {
			model.addAttribute(DUPLICATE_EMAIL_KEY, true);
		}
	}
}

package com.pc.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pc.enums.PlansEnum;
import com.pc.model.Plan;
import com.pc.model.frontend.ProAccountPayload;

@Controller
public class SignupController {

	public static final Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	public static final String SIGNUP_URL = "/signup";
	
	public static final String PAYLOAD_MODEL_KEY_NAME= "payload";
	
	public static final String SIGNUP_PAGE = "/signup";
	
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupGet(@RequestParam("planId") int planId, ModelMap model) {
		logger.debug("user signup page invoked");
		
		if(planId!=PlansEnum.BASIC.getId() && planId!=PlansEnum.ADVANCED.getId())
			throw new IllegalArgumentException("Plan is not valid");
		
		model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());
		return SIGNUP_PAGE;
	}
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signupPost(@RequestParam(name = "planId", required = true) int planId,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @ModelAttribute(PAYLOAD_MODEL_KEY_NAME) @Valid ProAccountPayload payload,
            ModelMap model) {
		logger.debug("user signup page invoked for saving user");
		return SIGNUP_PAGE;
	}
}

package com.pc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

	/**
	 * @return
	 */
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupget() {
		return "/signup";
	}
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup() {
		return null;
	}
}

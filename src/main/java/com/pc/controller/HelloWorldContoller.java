package com.pc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.enums.PlansEnum;
import com.pc.service.UserService;

@Controller
public class HelloWorldContoller {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/index")
	public String sayHelloWorld() {
		return "index";
	}
	
	@RequestMapping(value = "/home")
	public String home() {
		return "user/home";
	}
	
	@RequestMapping(value = "/")
	public String index() {
		userService.createUser();
		return "index";
	}
	
}

package com.pc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldContoller {

	@RequestMapping(value = "/index")
	public String sayHelloWorld() {
		return "index";
	}
	
	@RequestMapping(value = "/home")
	public String home() {
		return "user/home";
	}
}

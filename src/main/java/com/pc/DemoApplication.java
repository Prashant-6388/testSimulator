package com.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pc.service.UserService;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
public class DemoApplication {

	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

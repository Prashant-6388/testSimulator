package com.pc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityController extends WebSecurityConfigurerAdapter {

	//public URLS that do not require authentication
	public static final String[] PUBLIC_MATCHERS= {
		"/webjars/**",
		"/css/**",
		"/js/**",
		"/images/**",
		"/",
		"/error",
		"/index",
		"/h2/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll() //exclude from spring security...public access
			.anyRequest().authenticated() //for remaining request use authentication
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/home") //loginpage-> user custom login page
			.failureUrl("/error") //on error
			.permitAll()
			.and()
			.logout().permitAll();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.inMemoryAuthentication()
			.withUser("user")
			.password("{noop}password") //noop -> no password encryption
			.roles("USER")
			.and()
			.withUser("admin")
			.password("{noop}admin")
			.roles("USER","ADMIN");
	}
	
}

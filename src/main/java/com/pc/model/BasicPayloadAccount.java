package com.pc.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Prashant
 *
 */
public class BasicPayloadAccount {
	
	public static final long serialVersionID = 1L;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String confirmPassword;
	
	@NotNull
	private String country;

	private String description;
	
	@NotNull
	private String firstName;
	
	private String lastName;
	
	@NotNull
	private String phoneNumber;

	// private String profileImageUrl;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

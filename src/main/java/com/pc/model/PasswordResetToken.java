package com.pc.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pc.utils.LocalDateTimeAttributeConverter;

import groovy.util.logging.Log;

@Entity
public class PasswordResetToken implements Serializable {

	/**
	 *  Entity for handling tokens for Forgot password functionality
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);
	
	public static final int DEFAULT_EXPIRATION_MINUTES = 5;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String token;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "expiry_date")
	@Convert(converter= LocalDateTimeAttributeConverter.class)
	private LocalDateTime expiryDate;
	public long getId() {
		return id;
	}

	public PasswordResetToken() {
		
	}
	
	/**
	 * @param token The user token, it can not be null
	 * @param user The user, it can not be null
	 * @param creationDate The creation time, it can not be null
	 * @param expirationInMinute Time in minutes for which token will be valid
	 */
	public PasswordResetToken(String token, User user, LocalDateTime creationDate, int expirationInMinute) {
		if(null == token || user == null || creationDate == null ){
			throw new IllegalArgumentException("token, user or creation date can not be null");
		}
		if(expirationInMinute == 0)		{
			LOG.warn("expiration minutes is zero, assigning default value "+DEFAULT_EXPIRATION_MINUTES);
			expirationInMinute = DEFAULT_EXPIRATION_MINUTES;
		}
		this.token = token;
		this.user = user;
		this.expiryDate = creationDate.plusMinutes(expirationInMinute);
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordResetToken other = (PasswordResetToken) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

package com.pc.persistance;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	public final String authority;
	
	public Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}

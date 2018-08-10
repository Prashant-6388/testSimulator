package com.pc.enums;

public enum RolesEnum {

	BASIC(1,"ROLE_BASIC"),
	PRO(2,"ROLE_PRO"),
	ADMIN(3,"ROLE_ADMIN");
	
	private final int id;
	

	private final String name;
	
	RolesEnum(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

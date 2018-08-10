package com.pc.enums;

public enum PlansEnum {

	BASIC(1,"Basic Plan"),
	ADVANCED(2,"Advanced Plan");
	
	private final int id;
	

	private final String name;
	
	PlansEnum(int id, String name)
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

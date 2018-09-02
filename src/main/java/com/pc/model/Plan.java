package com.pc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.pc.enums.PlansEnum;

@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	
	public Plan() {
		
	}
	public Plan(PlansEnum planEnum)
	{
		this.id = planEnum.getId();
		this.name = planEnum.getName();
	}

	public String getPlanname() {
		return name;
	}

	public void setPlanname(String planname) {
		this.name = planname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

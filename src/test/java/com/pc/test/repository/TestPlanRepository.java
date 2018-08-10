package com.pc.test.repository;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.enums.PlansEnum;
import com.pc.enums.RolesEnum;
import com.pc.model.Plan;
import com.pc.model.Role;
import com.pc.model.User;
import com.pc.model.UserRole;
import com.pc.persistance.PlanRepository;
import com.pc.persistance.RoleRepository;
import com.pc.persistance.UserRepository;
import com.pc.utils.UserUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlanRepository {

	@Autowired 
	PlanRepository planRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@Transactional
	public void testBasicPlan()
	{
		Plan plan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(plan);
		Plan p = planRepository.getOne(PlansEnum.BASIC.getId());
		Assert.assertEquals(p.getPlanname(), PlansEnum.BASIC.getName());
		
	}
	
	public Plan createBasicPlan(PlansEnum planEnum)
	{
		Plan plan = new Plan();
		plan.setId(planEnum.getId());
		plan.setPlanname(planEnum.getName());
		return plan;
	}
	
	@Test
	@Transactional
	public void createUser()
	{
		Role basicRole = new Role(RolesEnum.BASIC);
		User basicUser = UserUtils.createBasicUser();
		Plan plan = createBasicPlan(PlansEnum.ADVANCED);
		planRepository.save(plan);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		for(UserRole role: userRoles)
			roleRepository.save(role.getRole());
		
		userRepository.save(basicUser);
		System.out.println("User = "+userRepository.getOne(basicUser.getId()).getUsername());
		
	}
	
}

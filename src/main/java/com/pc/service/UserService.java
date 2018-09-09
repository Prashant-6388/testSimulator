package com.pc.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public User createUser(User user,PlansEnum planEnum,Set<UserRole> userRoles) {
	
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		
		Plan plan = new Plan(planEnum);
		if(!planRepository.existsById(planEnum.getId()))
			planRepository.save(plan);
		
		user.setPlan(plan);
		
//		for(UserRole userRole : userRoles)
//			roleRepository.save(userRole.getRole());
//		
		userRoles.forEach(ur -> roleRepository.save(ur.getRole()));
		
		user.setUserRoles(userRoles);
		
		user.getUserRoles().addAll(userRoles);
		
		return user;
	}
	
    @Transactional
	public User createUser()
	{
		Role basicRole = new Role(RolesEnum.BASIC);
		User basicUser = createBasicUser();
		Plan plan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(plan);
		
		basicUser.setPlan(plan);
		//roleRepository.save(basicRole);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRoles.add(userRole);
	
		
		for(UserRole role: userRoles)
			roleRepository.save(role.getRole());
		
		basicUser.getUserRoles().addAll(userRoles);
		
		
		userRepository.save(basicUser);
		return basicUser;
		//System.out.println("User = "+userRepository.getOne(basicUser.getId()).getUsername());
	}

	public Plan createBasicPlan(PlansEnum planEnum)
	{
		Plan plan = new Plan();
		plan.setId(planEnum.getId());
		plan.setPlanname(planEnum.getName());
		return plan;
	}
	
	public User createBasicUser() {
		User user = new User();
		user.setUsername("prashant");
		String password = passwordEncoder.encode("prashant");
		user.setPassword(password);
		user.setEnabled(true);
		user.setEmail("prashant.6388@gmail.com");
		return user;
	}
	
	
}

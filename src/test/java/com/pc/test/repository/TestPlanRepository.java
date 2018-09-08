package com.pc.test.repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.enums.PlansEnum;
import com.pc.enums.RolesEnum;
import com.pc.model.PasswordResetToken;
import com.pc.model.Plan;
import com.pc.model.Role;
import com.pc.model.User;
import com.pc.model.UserRole;
import com.pc.persistance.PasswordRestTokenRepository;
import com.pc.persistance.PlanRepository;
import com.pc.persistance.RoleRepository;
import com.pc.persistance.UserRepository;
import com.pc.service.UserService;
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
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordRestTokenRepository tokenRepository;
	
	@Value("${token.expiration.length.minute}")
	private int expirationTime;
	
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
		System.out.println("User = "+userRepository.getOne((int) basicUser.getId()).getUsername());
		
	}
	
	@Test
	@Transactional
	public void testUserService()
	{
		User user = userService.createUser();
		System.out.println("User -----> "+user.getUserRoles().size());
	}
	

	@Test
	@Transactional
	public void testTokenService()
	{
		User user = userService.createUser();
		
		LocalDateTime currentTime = LocalDateTime.now();
		String token = UUID.randomUUID().toString();
		System.out.println("token generated = "+token);
		
		String token1 = UUID.randomUUID().toString();
		System.out.println("token generated = "+token1);
		
		String token2 = UUID.randomUUID().toString();
		System.out.println("token generated = "+token2);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, currentTime, expirationTime);
		PasswordResetToken passwordResetToken1 = new PasswordResetToken(token1, user, currentTime, expirationTime);
		PasswordResetToken passwordResetToken2 = new PasswordResetToken(token2, user, currentTime, expirationTime);
		
		tokenRepository.save(passwordResetToken);
		tokenRepository.save(passwordResetToken1);
		tokenRepository.save(passwordResetToken2);

		Set<PasswordResetToken> tokensFromDb = tokenRepository.findAllByUserId(user.getId());
		Iterator<PasswordResetToken> itr = tokensFromDb.iterator();
		while(itr.hasNext())
		{
			System.out.println("tokenFromDb = "+((PasswordResetToken)itr.next()).getToken());
			System.out.println("tokenFromDb = "+((PasswordResetToken)itr.next()).getExpiryDate());
		}
				
	}
	
}

package com.pc.test.repository;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.model.Plan;
import com.pc.persistance.PlanRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlanRepository {

	@Autowired 
	PlanRepository planRepository;
	
	@Test
	@Transactional
	public void testBasicPlan()
	{
		Plan plan = createBasicPlan();
		planRepository.save(plan);
		Plan p = planRepository.getOne(1);
		Assert.assertEquals(p.getPlanname(), "Plan 1");
		
	}
	
	public Plan createBasicPlan()
	{
		Plan plan = new Plan();
		plan.setId(1);
		plan.setPlanname("Plan 1");
		return plan;
	}
}

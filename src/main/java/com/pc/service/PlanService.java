package com.pc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.enums.PlansEnum;
import com.pc.model.Plan;
import com.pc.persistance.PlanRepository;

@Service
@Transactional(readOnly = true)
public class PlanService {

	@Autowired
	private PlanRepository planRepository;
	
	public Plan findPlanById(Integer planId) {
		return planRepository.findById(planId).orElse(null);
	}
	
	@Transactional
	public Plan createPlan(int planId)
	{
		Plan plan=null;
		if(planId==1)
			plan = planRepository.save(new Plan(PlansEnum.BASIC));
		else if(planId==2)
			plan = planRepository.save(new Plan(PlansEnum.ADVANCED));
		else
			throw new IllegalArgumentException("Plan id "+planId+" not recognised");
		
		return plan;
	}
	

	public PlanRepository getPlanRepository() {
		return planRepository;
	}

	public void setPlanRepository(PlanRepository planRepository) {
		this.planRepository = planRepository;
	}
	
}

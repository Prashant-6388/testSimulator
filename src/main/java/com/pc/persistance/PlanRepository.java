package com.pc.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.Plan;

@Repository
public interface PlanRepository extends  CrudRepository<Plan, Integer>{

}

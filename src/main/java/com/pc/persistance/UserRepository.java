package com.pc.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<UserRole, Long>{

}

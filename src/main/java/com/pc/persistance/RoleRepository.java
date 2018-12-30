package com.pc.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}

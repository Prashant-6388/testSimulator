package com.pc.persistance;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pc.model.PasswordResetToken;

@Repository
public interface PasswordRestTokenRepository extends JpaRepository<PasswordResetToken, Integer>{
	
	PasswordResetToken findByToken(String token);
	
	@Query("select ptr from PasswordResetToken ptr inner join ptr.user u where ptr.user.id = ?1")
	Set<PasswordResetToken> findAllByUserId(long userId);
}

package com.pc.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pc.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * @param username
	 * @return User
	 */
	public User findUserByUsername(String username);
	
	/**
	 * Search User by email address
	 * @param email
	 * @return User
	 */
	public User findByEmail(String email);
	
	@Modifying
	@Query("update User u set u.password = :password where u.id = :userId")
	public void updateUserPassword(@Param("userId") long userId, @Param("password") String password);
}

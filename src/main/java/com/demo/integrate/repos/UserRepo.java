package com.demo.integrate.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.integrate.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	public User getUserByUsername(String username);

}

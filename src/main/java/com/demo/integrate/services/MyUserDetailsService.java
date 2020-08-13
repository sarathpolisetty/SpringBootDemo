package com.demo.integrate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.integrate.entities.User;
import com.demo.integrate.repos.MyUserDetails;
import com.demo.integrate.repos.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=repo.getUserByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("No user found");
		}
		return new MyUserDetails(user);
	}
	
	
	public void insertUser(User user) {
		repo.save(user);
	}
	

}

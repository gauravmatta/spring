package com.springimplant.jwt.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springimplant.jwt.api.entity.User;
import com.springimplant.jwt.api.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<User> userObj = repository.findByUserId(username);
	User user = userObj.get();
	return new org.springframework.security.core.userdetails.User(user.getUserId(),new BCryptPasswordEncoder().encode(user.getUserId()),new ArrayList<>());
	}
	

}

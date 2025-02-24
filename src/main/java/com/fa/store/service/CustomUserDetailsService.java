package com.fa.store.service;

import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fa.store.entity.CustomUserDetails;
import com.fa.store.entity.User;
import com.fa.store.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByUserName(username);
		if(Objects.isNull(user)) {
			System.out.println("User not available");
			throw new UsernameNotFoundException("User is not exists ");
		}
			return new CustomUserDetails(user);

	}

}

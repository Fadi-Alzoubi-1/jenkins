package com.fa.store.service;

import org.springframework.stereotype.Service;

import com.fa.store.entity.User;


public interface UserService {
	
	User userRegister(User user);
	String userLogin(User user);
	String getByUsername(User user);
	String generateUserJwtToken(User user);
	String verify(User user);

}

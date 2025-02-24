package com.fa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.store.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	 @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
	User findByUserName(String username);
}

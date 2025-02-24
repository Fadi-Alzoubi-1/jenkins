package com.fa.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.fa.stroe.config.JwtAuthenticationFilter;

import com.fa.stroe.config.WebSecurityConfig;

import ch.qos.logback.classic.Logger;
@Import(value = {WebSecurityConfig.class, JwtAuthenticationFilter.class}) 
@SpringBootApplication
public class StoreApplication {
	 
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
		
	}

}

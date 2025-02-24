package com.fa.stroe.config;

import org.aspectj.weaver.ast.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

import com.fa.store.contoller.StoreController;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("JSESSIONID");
	HeaderWriterLogoutHandler clearSiteData =
			new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES, Directive.ALL));
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	public WebSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
//	 protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .headers()
//	            .referrerPolicy(ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN);
//	    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and()
		//.headers().referrerPolicy(ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN).and()
		.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/login*", "/Logout","/register","/error").permitAll().anyRequest().authenticated();
			
		}).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.oauth2Login(Customizer.withDefaults())
	//	.logout(logout -> {
			//
			// http://localhost:8080/oauth2/authorization/google
			// https://accounts.google.com/Logout?ec=GAdAwAE&hl=en
			// logout.logoutUrl("https://www.google.com/accounts/Logout");
			// logout.addLogoutHandler(logoutHandler());
		//	logout.logoutUrl("http://localhost:8080/oauth2/authorization/google");
			// logout.logoutSuccessUrl("http://localhost:8080/home");
	//	})
				// logout.backChannel(Customizer.withDefaults()))
//				.formLogin(from -> {
//					 Customizer.withDefaults();
//					// from.defaultSuccessUrl("/getProduct/69",true);
//				})
	//	.httpBasic(Customizer.withDefaults());
				
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		logger.info(this.getClass().getMethods()+"\n*********** 	this.jwtAuthenticationFilter ***********\n" + 	userDetailsService.toString());
		return httpSecurity.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userDetailsService);
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		// provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(14);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		logger.info(this.getClass().getMethods()+"\n*********** 	authenticationConfiguration ***********\n" + 	authenticationConfiguration.toString());
		return authenticationConfiguration.getAuthenticationManager();
	}

}

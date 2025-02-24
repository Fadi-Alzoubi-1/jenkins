package com.fa.store.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fa.store.entity.Product;
import com.fa.store.entity.User;
import com.fa.store.service.UserService;
import com.nimbusds.oauth2.sdk.Request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@CrossOrigin
@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(StoreController.class);

	RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("our-custom-cookie");
	
//	@GetMapping("/")
//	public RedirectView getLogin() {
//		
//		return new RedirectView("http://localhost:8080/home.html");
//		
//	}
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		userService.userRegister(user);
		return "User successfully registred: ";
	}


//	@GetMapping("")
//	public RedirectView defaualt() {
//		// HttpEntity<Product> request = new HttpEntity<>(Product);
////		ResponseEntity<Product> product = restTemplate.getForEntity(, Product.class);
//		if(auth!=null) {
//		authenticationManager.authenticate(auth);
//		if(auth.isAuthenticated()==true)
//		return new RedirectView("http://localhost:8080/getProduct/69");
//		}
//		return new RedirectView("http://localhost:8080/login"); 
//	}



	@PostMapping(value="/login", produces="Application/JSON")
	public String userLogin(@RequestBody User user) {

		return userService.verify(user);
		
	
	}
	
	
	@PostMapping("/logout")
	public RedirectView getLogouton() {
		
		return new RedirectView("http://localhost:8080/index.html");
	}
//	@PostMapping("/logout")
//	public RedirectView userLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
//		//this.logoutHandler.logout(request, response, authentication);
//		// this.logoutHandler.);
//		authentication.setAuthenticated(false);
//		request.getSession().invalidate();
//		  return new RedirectView("https://accounts.google.com/");
//	}	
	
	@GetMapping("/logout3")
	public ModelAndView userLogout3() {
	    ModelAndView modelAndView = new ModelAndView("hello"); // "hello" is the view name
        modelAndView.addObject("message", "Hello from Spring!"); // Add data to the model
        return modelAndView;
	}
	

	@GetMapping("/googellogin")
	public RedirectView userGoogelLogin() {
		// return userService.getByUsername(user);
		// return userService.generateUserJwtToken(user);
		
		  return new RedirectView("https://accounts.google.com/");
		
	}

	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest request) {

		return (CsrfToken) request.getAttribute("_csrf");
	}

	

}

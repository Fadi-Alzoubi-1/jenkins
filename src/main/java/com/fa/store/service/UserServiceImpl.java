package com.fa.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fa.store.entity.User;
import com.fa.store.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthenticationManager authenticationMananger;

	@Autowired
	JwtService jwtService;

	public User userRegister(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public String userLogin(User user) {
		return "failed";
	}

	@Override
	public String getByUsername(User user) {

		User userRepo = userRepository.findByUserName(user.getUsername());

		if (userRepo != null)
			return "User logedin successfuly " + user.getUsername();
		return "User loggedin Failed " + user.getUsername();
	}

	@Override
	public String generateUserJwtToken(User user) {
		Authentication authenticate = authenticationMananger
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authenticate.isAuthenticated())
			return jwtService.generateToken(user);

		return "failure";
	}
	@Override
    public String verify(User user) {
        Authentication authenticate
                = authenticationMananger.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );

        //var u = userRepository.findByUserName(user.getUserName());
        if(authenticate.isAuthenticated())
            return jwtService.generateToken(user);
        return "failure";
    }

}

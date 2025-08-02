package com.portfolio.portfoliogenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.UserRegisterDto;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.util.Capitalizer;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Capitalizer capitalizer;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	public void registerUser(UserRegisterDto userRegisterDto) {
	    User user = new User();
	    user.setEmail(userRegisterDto.getEmail());
	    user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
	    user.setFullName(capitalizer.capitalise(userRegisterDto.getFullName()));
	    
	    userRepository.save(user);  // Let this throw exception if email exists
	}
	

}

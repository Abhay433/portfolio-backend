package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.dto.UserDto;
import com.portfolio.portfoliogenerator.model.User;


public interface UserService {
	
	void saveUserProfile(UserDto userDto);
	
	List<User> getAllUser(UserDto userDto);
	
	void updateUserById(Long id ,UserDto userdto);

	void deleteUserById(Long id);
	
	UserDto getUserById(Long id);
	
	
}

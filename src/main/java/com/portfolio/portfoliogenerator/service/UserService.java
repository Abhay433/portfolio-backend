package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.portfoliogenerator.dto.UserBasicDto;
import com.portfolio.portfoliogenerator.dto.UserDto;
import com.portfolio.portfoliogenerator.model.User;


public interface UserService {
	
	User saveUserProfile(UserDto userDto);
	
	List<User> getAllUser(UserDto userDto);
	
	public User updateUserById(Long id ,UserDto userdto);

	void deleteUserById(Long id);
	
	UserDto getUserById(Long id);
	
	User updateUserBasicDetails(Long id,UserBasicDto userBasicDto);
	
	void uploadProfileImage(Long userId, MultipartFile file);
	
	User saveUserProfileWithImage(UserDto userDto, MultipartFile file);


	
	
}

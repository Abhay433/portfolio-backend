package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.dto.UserDto;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.service.UserService;


@RestController
@RequestMapping("/usercontroller")
@CrossOrigin(origins = "*")
public class UserController {

	 	@Autowired
	    private UserService userService;

	    @PostMapping("/createuser")
	    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
	    	
	        userService.saveUserProfile(userDto);
	        
	        return ResponseEntity.ok("User profile saved successfully.");
	    }
	    
	    @GetMapping("/users")
	    public List<User> getAllUser(@RequestBody UserDto userDto) {
	        
	        return userService.getAllUser(userDto);
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteById(@PathVariable long id) {
	        userService.deleteUserById(id);
	        return ResponseEntity.ok("User deleted successfully");
	    }
	    
	    
	    @PutMapping("/update/{id}")
	    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody UserDto userDto) {
	        userService.updateUserById(id, userDto);
	        return ResponseEntity.ok("User updated successfully");
	    }
	    
	    @GetMapping("/userportfolio/{id}")
	    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
	        UserDto userDto = userService.getUserById(id);
	        return ResponseEntity.ok(userDto);
	    }

	    
	    
	    
	    
}


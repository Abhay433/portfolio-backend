package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.dto.UserBasicDto;
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
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        try {
            User savedUser = userService.saveUserProfile(userDto);
            return ResponseEntity.ok().body(
                new ApiResponse(true, "User profile saved successfully.", savedUser.getId())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error saving user profile: " + e.getMessage())
            );
        }
    }



    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(@RequestBody UserDto userDto) {
        try {
            List<User> users = userService.getAllUser(userDto);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error fetching users: " + e.getMessage())
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error deleting user: " + e.getMessage())
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            userService.updateUserById(id, userDto);
            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error updating user: " + e.getMessage())
            );
        }
    }

    @GetMapping("/userportfolio/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(false, "User not found: " + e.getMessage())
            );
        }
    }
    
    
    @PutMapping("/updatebasicdetails/{id}")
    public ResponseEntity<ApiResponse> updateUserBasicDetails(@PathVariable Long id, @RequestBody UserBasicDto userBasicDto) {
        try {
            userService.updateUserBasicDetails(id, userBasicDto);
            ApiResponse response = new ApiResponse(true, "User updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error updating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Optional: Response wrapper class
    public static class ApiResponse {
        private boolean success;
        private String message;
        private Long userId; // optional, only for create

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(boolean success, String message, Long userId) {
            this.success = success;
            this.message = message;
            this.userId = userId;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Long getUserId() { return userId; }
    }
    
    
}
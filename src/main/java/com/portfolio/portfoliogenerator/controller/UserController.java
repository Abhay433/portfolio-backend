package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    public ResponseEntity<User> updateUserBasicDetails(
            @PathVariable Long id,
            @RequestBody UserBasicDto userBasicDto) {
        try {
            User updatedUser = userService.updateUserBasicDetails(id, userBasicDto);
            return ResponseEntity.ok(updatedUser); // ✅ return updated user to frontend
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    // to updateuser profileimage
    @PutMapping("/uploadprofile/{userId}")
    public ResponseEntity<ApiResponse> uploadProfileImage(
            @PathVariable Long userId,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            userService.uploadProfileImage(userId, imageFile);
            return ResponseEntity.ok(new ApiResponse(true, "Profile image uploaded successfully.",userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to upload profile image: " + e.getMessage()));
        }
    }
    
    
    
    @PostMapping("/createuserwithimage")
    public ResponseEntity<?> saveUserWithImage(
            @RequestPart("user") UserDto userDto,
            @RequestPart("image") MultipartFile file) {
        try {
            User savedUser = userService.saveUserProfileWithImage(userDto, file);
            return ResponseEntity.ok().body(
                    new ApiResponse(true, "User profile saved successfully with image.", savedUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "Error saving user profile: " + e.getMessage()));
        }
    }


    

    
    // Optional: Response wrapper class
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
package com.portfolio.portfoliogenerator.controller;

import com.portfolio.portfoliogenerator.security.JwtUtil;
import com.portfolio.portfoliogenerator.service.AuthService;
import com.portfolio.portfoliogenerator.dto.AuthRequest;
import com.portfolio.portfoliogenerator.dto.UserRegisterDto;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.security.CustomUserDetails;
import com.portfolio.portfoliogenerator.security.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    

    // 🔐 Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // 1. Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
                )
            );

            // 2. Generate JWT token
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // 3. Get user ID (you may need to fetch full User entity from DB)
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 4. Return token and userId in response
            Map<String, Object> response = new HashMap<>();
            response.put("token", "Bearer " + token);
            response.put("userId", user.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse(false, "Invalid email or password")
            );
        }
    }
    
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            authService.registerUser(userRegisterDto);
            return ResponseEntity.ok(new ApiResponse(true, "Account created successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error creating account: " + e.getMessage())
            );
        }
    }
    
  //Inner class for consistent API responses
    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }

    }
    
}


    

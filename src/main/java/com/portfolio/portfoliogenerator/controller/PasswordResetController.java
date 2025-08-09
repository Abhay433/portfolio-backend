package com.portfolio.portfoliogenerator.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.model.PasswordResetToken;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.service.PasswordResetTokenService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/request")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User with this email doesn't exist.");
        }

        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetToken(userOpt.get(), token);

        // In real app, you'd send this via email
//        String resetLink = "https://myportfoliogenerator.netlify.app/reset-password?token=" + token;

//        for local 
        String resetLink = "http://localhost:4200/reset-password?token=" + token;


        return ResponseEntity.ok("Password reset link: " + resetLink);
    }

    
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        // 1. Validate the reset token
        PasswordResetToken resetToken = passwordResetTokenService.validatePasswordResetToken(token);
        User tokenUser = resetToken.getUser();

        // 2. Update the password
        tokenUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(tokenUser);

        return ResponseEntity.ok("✅ Password has been reset successfully.");
    }


}


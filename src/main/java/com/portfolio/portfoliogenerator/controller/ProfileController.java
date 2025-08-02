package com.portfolio.portfoliogenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.service.ProfileService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/has-profile/{userId}")
    public ResponseEntity<Boolean> hasProfile(@PathVariable Long userId) {
        boolean exists = profileService.doesProfileExist(userId);
        return ResponseEntity.ok(exists);
    }
}


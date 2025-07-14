package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.service.ExperienceService;

@RestController
@RequestMapping("/experiencecontroller")
@CrossOrigin(origins = "*")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/addExperience/{userId}")
    public ResponseEntity<?> addExperience(@RequestBody ExperienceDto experienceDto, @PathVariable Long userId) {
        try {
            experienceService.addExperience(experienceDto, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Experience added successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error adding experience: " + e.getMessage()));
        }
    }

    @GetMapping("/userexperience/{userId}")
    public ResponseEntity<?> getExperienceByUserId(@PathVariable Long userId) {
        try {
            List<Experience> experienceList = experienceService.getExperienceByUserId(userId);
            return ResponseEntity.ok(experienceList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error fetching experience: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{userId}/{experienceId}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        try {
            experienceService.deleteExperienceByUserIdAndExperienceId(userId, experienceId);
            return ResponseEntity.ok(new ApiResponse(true, "Experience deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error deleting experience: " + e.getMessage()));
        }
    }

    @PutMapping("/user/{userId}/experience/{experienceId}")
    public ResponseEntity<?> updateExperienceByUserIdAndExperienceId(
            @PathVariable Long userId,
            @PathVariable Long experienceId,
            @RequestBody ExperienceDto experienceDto) {
        try {
            experienceService.updateExperienceByUserId(userId, experienceId, experienceDto);
            return ResponseEntity.ok(new ApiResponse(true, "Experience updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error updating experience: " + e.getMessage()));
        }
    }

    // ✅ Reusable response class
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

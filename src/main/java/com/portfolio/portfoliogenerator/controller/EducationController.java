package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.service.EducationService;

@RestController
@RequestMapping("/educationcontroller")
@CrossOrigin(origins = "*")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping("/usereducation/{id}")
    public ResponseEntity<?> getEducationByUserId(@PathVariable Long id) {
        try {
            List<Education> educationList = educationService.getEducationByUserId(id);
            return ResponseEntity.ok(educationList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error fetching education: " + e.getMessage())
            );
        }
    }

    @PostMapping("/addEducation/{id}")
    public ResponseEntity<?> addEducation(@RequestBody EducationDto educationDto, @PathVariable Long id) {
        try {
            educationService.addEducation(educationDto, id);
            return ResponseEntity.ok(new ApiResponse(true, "Education added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error adding education: " + e.getMessage())
            );
        }
    }

    @DeleteMapping("/delete/{userId}/{educationId}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long userId, @PathVariable Long educationId) {
        try {
            educationService.deleteEducationByUserIdAndEducationId(userId, educationId);
            return ResponseEntity.ok(new ApiResponse(true, "Education deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error deleting education: " + e.getMessage())
            );
        }
    }

    @PutMapping("/user/education/{userId}/{educationId}")
    public ResponseEntity<Education> updateEducation(
        @PathVariable Long userId,
        @PathVariable Long educationId,
        @RequestBody EducationDto educationDto) {
        
        try {
            Education education=educationService.updateEducationByUserId(userId, educationId, educationDto);
            return ResponseEntity.ok(education);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            
        }
    }

    // Inner class for consistent API responses
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

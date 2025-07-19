package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.service.SkillService;

@RestController
@RequestMapping("/skillcontroller")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/addSkill/{userId}")
    public ResponseEntity<?> addSkill(@RequestBody SkillDto skillDto, @PathVariable Long userId) {
        try {
            skillService.addSkill(skillDto, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Skill added successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error adding skill: " + e.getMessage()));
        }
    }

    @GetMapping("/userskill/{userId}")
    public ResponseEntity<?> getSkillsByUserId(@PathVariable Long userId) {
        try {
            List<Skill> skillList = skillService.getSkillByUserId(userId);
            return ResponseEntity.ok(skillList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error fetching skills: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{userId}/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long userId, @PathVariable Long skillId) {
        try {
            skillService.deleteSkillByUserIdAndSkillId(userId, skillId);
            return ResponseEntity.ok(new ApiResponse(true, "Skill deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error deleting skill: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<List<Skill>> updateSkill(@PathVariable Long userId, @RequestBody List< SkillDto> skillDto) {
        try {
        	List<Skill> updatedSkill =skillService.updateSkillByUserId(userId, skillDto);
        	ResponseEntity<List<Skill>> response= ResponseEntity.ok(updatedSkill);
        	return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Response Wrapper
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

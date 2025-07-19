package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.portfolio.portfoliogenerator.dto.ProjectDto;
import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.service.ProjectService;

@RestController
@RequestMapping("/projectcontroller")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/addProject/{userId}")
    public ResponseEntity<?> addProject(@RequestBody ProjectDto projectDto, @PathVariable Long userId) {
        try {
            projectService.addProject(projectDto, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Project added successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error adding project: " + e.getMessage()));
        }
    }

    @GetMapping("/userproject/{userId}")
    public ResponseEntity<?> getProjectByUserId(@PathVariable Long userId) {
        try {
            List<Project> projectList = projectService.getProjectByUserId(userId);
            return ResponseEntity.ok(projectList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error fetching project: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{userId}/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long userId, @PathVariable Long projectId) {
        try {
            projectService.deleteProjectByUserIdAndProjectId(userId, projectId);
            return ResponseEntity.ok(new ApiResponse(true, "Project deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(false, "Error deleting project: " + e.getMessage()));
        }
    }

    @PutMapping("/updateproject/{userId}")
    public ResponseEntity <List<Project>> updateProjectByUserIdAndProjectId(
            @PathVariable Long userId,
            @RequestBody List<ProjectDto> projectDto) {
        try {
           List< Project> project=projectService.updateProjectByUserIdAndProjectId(userId, projectDto);
             ResponseEntity<List<Project>> response=ResponseEntity.ok(project);
             return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }

    // ✅ Common reusable response class
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

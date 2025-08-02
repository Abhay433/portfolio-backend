package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.ProjectDto;
import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.ProjectRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.util.Capitalizer;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	Capitalizer capitalize;
	
	@Autowired
	ProjectRepository projectRepository;
	

	@Autowired
	UserRepository userRepository;

	public List<Project> getProjectByUserId(Long id){
		
		List<Project> project =projectRepository.findByUser_id(id);
		
		return project;
		
	}
	
	@Override
    public void addProject(ProjectDto projectDto, Long userId) {
		
		
		 // Get current logged-in user's email from JWT token
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserEmail = auth.getName();

	    // Fetch logged-in user from DB
	    User currentUser = userRepository.findByEmail(currentUserEmail)
	        .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

	    // Check if the logged-in user owns the userId being passed
	    if (!currentUser.getId().equals(userId)) {
	        throw new AccessDeniedException("❌ You are not authorized to add a project for another user.");
	    }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Project project = new Project();
        project.setTitle( capitalize.capitalise(projectDto.getTitle()));
        project.setDescription(capitalize.capitalise(projectDto.getDescription()));
        project.setTechnologiesUsed(capitalize.capitalise(projectDto.getTechnologiesUsed()));
        project.setProjectUrl(projectDto.getProjectUrl());
        project.setUser(user); // Linking user

        projectRepository.save(project);
    }
		
	
	@Override
    public void deleteProjectByUserIdAndProjectId(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        

        // Step 1: Get currently authenticated user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();

        // Step 2: Fetch the logged-in user's details
        User loggedInUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        
     // Step 4: Check if the project belongs to the logged-in user
        if (!project.getUser().getId().equals(loggedInUser.getId())) {
            throw new AccessDeniedException("❌ You are not authorized to delete this project.");
        }

        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("This project doesn't belong to the given user.");
        }

        projectRepository.deleteById(projectId);
    }
	
	
	
	@Transactional
	@Override
	public List<Project> updateProjectByUserIdAndProjectId(Long userId, List<ProjectDto> updatedProjectDtoList) {
		 // 🔐 Get authenticated user
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInEmail = authentication.getName();

	    // 🔐 Fetch the user from DB
	    User loggedInUser = userRepository.findByEmail(loggedInEmail)
	        .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

	    // 🔐 Check if logged-in user matches the userId being updated
	    if (!loggedInUser.getId().equals(userId)) {
	        throw new AccessDeniedException("❌ You are not authorized to update projects for this user.");
	    }
	    // Delete old projects linked to this user
	    projectRepository.deleteByUser_Id(userId);

	    List<Project> updatedProjects = new ArrayList<>();

	    for (ProjectDto dto : updatedProjectDtoList) {
	        Project project = new Project();

	        // Set user reference
	        project.setUser(loggedInUser);

	        // Apply null-safe values
	        project.setTitle(dto.getTitle() != null ? capitalize.capitalise(dto.getTitle()) : null);
	        project.setDescription(dto.getDescription() != null ? capitalize.capitalise(dto.getDescription()) : null);
	        project.setTechnologiesUsed(dto.getTechnologiesUsed() != null ? capitalize.capitalise(dto.getTechnologiesUsed()) : null);
	        project.setProjectUrl(dto.getProjectUrl() != null ? dto.getProjectUrl() : null);

	        updatedProjects.add(project);
	    }

	    return projectRepository.saveAll(updatedProjects);
	}




}

package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("This project doesn't belong to the given user.");
        }

        projectRepository.deleteById(projectId);
    }
	
	
	
	@Transactional
	@Override
	public List<Project> updateProjectByUserIdAndProjectId(Long userId, List<ProjectDto> updatedProjectDtoList) {
	    // Fetch user
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    // Delete old projects linked to this user
	    projectRepository.deleteByUser_Id(userId);

	    List<Project> updatedProjects = new ArrayList<>();

	    for (ProjectDto dto : updatedProjectDtoList) {
	        Project project = new Project();

	        // Set user reference
	        project.setUser(user);

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

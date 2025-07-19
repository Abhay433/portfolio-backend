package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.ProjectDto;
import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.ProjectRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;

@Service
public class ProjectServiceImpl implements ProjectService{
	
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
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setTechnologiesUsed(projectDto.getTechnologiesUsed());
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
	
	
	@Override
	public List<Project> updateProjectByUserIdAndProjectId(Long userId, List<ProjectDto> updatedProjectDtoList) {
	    // Get all existing projects by user ID
	    List<Project> existingProjects = projectRepository.findByUser_id(userId);

	    // Ensure the number of projects matches
	    if (existingProjects.size() != updatedProjectDtoList.size()) {
	        throw new RuntimeException("Mismatch between existing and incoming project count");
	    }

	    // Loop and update each project with data from its corresponding DTO
	    for (int i = 0; i < existingProjects.size(); i++) {
	        Project project = existingProjects.get(i);
	        ProjectDto dto = updatedProjectDtoList.get(i);

	        if (dto.getTitle() != null) project.setTitle(dto.getTitle());
	        if (dto.getDescription() != null) project.setDescription(dto.getDescription());
	        if (dto.getTechnologiesUsed() != null) project.setTechnologiesUsed(dto.getTechnologiesUsed());
	        if (dto.getProjectUrl() != null) project.setProjectUrl(dto.getProjectUrl());
	    }

	    // Save all updated projects
	    return projectRepository.saveAll(existingProjects);
	}



}

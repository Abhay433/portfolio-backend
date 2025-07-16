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
	
	
	
	public Project updateProjectByUserIdAndProjectId(Long userId, Long projectId, ProjectDto updatedProject) {
	    Project existingProject = projectRepository.findById(projectId)
	            .orElseThrow(() -> new RuntimeException("Project not found"));

	    if (!existingProject.getUser().getId().equals(userId)) {
	        throw new RuntimeException("User ID mismatch with project owner");
	    }

	    if (updatedProject.getTitle() != null) {
	        existingProject.setTitle(updatedProject.getTitle());
	    }

	    if (updatedProject.getDescription() != null) {
	        existingProject.setDescription(updatedProject.getDescription());
	    }

	    if (updatedProject.getTechnologiesUsed() != null) {
	        existingProject.setTechnologiesUsed(updatedProject.getTechnologiesUsed());
	    }

	    if (updatedProject.getProjectUrl() != null) {
	        existingProject.setProjectUrl(updatedProject.getProjectUrl());
	    }

	    projectRepository.save(existingProject);
	    return existingProject;
	}


}

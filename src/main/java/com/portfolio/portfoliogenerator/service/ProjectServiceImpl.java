package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.repo.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	ProjectRepository projectRepository;

	public List<Project> getProjectByUserId(Long id){
		
		List<Project> project =projectRepository.findByUser_id(id);
		
		return project;
		
	}
}

package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.model.Project;

public interface ProjectService {
	
	public List<Project> getProjectByUserId(Long id);
}

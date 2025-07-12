package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.service.ProjectService;

@RestController
@RequestMapping("/projectcontroller")
@CrossOrigin(origins = "*")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@GetMapping("/userproject/{id}")
	public List<Project> getExperienceByUserId(@PathVariable Long id){
		List<Project> project=projectService.getProjectByUserId(id);
		
		return project;
	

	}
}

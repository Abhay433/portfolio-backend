package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.service.EducationService;

@RestController
@RequestMapping("/educationcontroller")
@CrossOrigin(origins = "*")
public class EducationController {

	@Autowired
	EducationService educationservice;
	
	@GetMapping("/usereducation/{id}")
	public List<Education> getEducationByUserId(@PathVariable Long id){
		
		return educationservice.getEducationByUserId(id);
		
	}
}

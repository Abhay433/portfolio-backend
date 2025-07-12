package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.service.ExperienceService;


@RestController
@RequestMapping("/experiencecontroller")
@CrossOrigin(origins = "*")
public class ExperienceController {
	
	@Autowired
	ExperienceService experienceService;
	
	@GetMapping("/userexperience/{id}")
	public List<Experience> getExperienceByUserId(@PathVariable Long id){
		List<Experience> experience=experienceService.getExperienceByUserId(id);
		
		return experience;
	}

	@PostMapping("/addExperience/{userId}")
    public ResponseEntity<String> addExperience(@RequestBody ExperienceDto experienceDto, @PathVariable Long userId) {
        experienceService.addExperience(experienceDto, userId);
        return ResponseEntity.ok("Experience added successfully");
    }
	

	
}

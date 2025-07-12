package com.portfolio.portfoliogenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.service.EducationService;

@RestController
@RequestMapping("/educationcontroller")
@CrossOrigin(origins = "*")
public class EducationController {

	@Autowired
	EducationService educationService;
	
	@GetMapping("/usereducation/{id}")
	public List<Education> getEducationByUserId(@PathVariable Long id){
		
		return educationService.getEducationByUserId(id);
		
	}
	
	
	@PostMapping("/addEducation/{id}")
    public ResponseEntity<String> addEducation(@RequestBody EducationDto educationDto, @PathVariable Long id) {
		System.out.println(educationDto);
		
        educationService.addEducation(educationDto, id);
        
        return ResponseEntity.ok("Education added successfully");
    }
	
	
	@DeleteMapping("/delete/{userId}/{educationId}")
	public ResponseEntity<String> deleteEducation(
	        @PathVariable Long userId,
	        @PathVariable Long educationId) {
	    
	    educationService.deleteEducationByUserIdAndEducationId(userId, educationId);
	    return ResponseEntity.ok("Education deleted successfully");
	}

}

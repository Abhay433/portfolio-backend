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

import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.service.SkillService;

@RestController
@RequestMapping("/skillcontroller")
@CrossOrigin(origins = "*")
public class SkillController {
	
	@Autowired
	SkillService skillService;
	
	@GetMapping("/userskill/{id}")
	public List<Skill> getExperienceByUserId(@PathVariable Long id){
		List<Skill> skill=skillService.getSkillByUserId(id);
		
		return skill;

	}
	
	@PostMapping("/addSkill/{userId}")
    public ResponseEntity<String> addSkill(@RequestBody SkillDto skillDto, @PathVariable Long userId) {
	      skillService.addSkill(skillDto, userId);
	      return ResponseEntity.ok("Skill added successfully");
	  }

}

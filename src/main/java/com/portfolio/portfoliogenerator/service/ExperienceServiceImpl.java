package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.repo.ExperienceRepository;


@Service
public class ExperienceServiceImpl implements ExperienceService {
	
	@Autowired
	ExperienceRepository experienceRepository;
	
	public List<Experience> getExperienceByUserId(Long id){
		
		List<Experience> experience=experienceRepository.findByUser_id(id);
		
		return experience;
		
		
		
	}

}

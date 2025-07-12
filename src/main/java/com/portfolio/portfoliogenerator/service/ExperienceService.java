package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.model.Experience;

public interface ExperienceService {
	
	public List<Experience> getExperienceByUserId(Long id);

}

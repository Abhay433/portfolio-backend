package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.model.Education;

public interface EducationService {
	
	public List<Education> getEducationByUserId(Long id);

}

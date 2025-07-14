package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;

public interface ExperienceService {
	
	public List<Experience> getExperienceByUserId(Long id);
	
	public void addExperience(ExperienceDto experienceDto,Long id);
	
	public void deleteExperienceByUserIdAndExperienceId(Long userId, Long experienceId);
	
	public void updateExperienceByUserId(Long userId, Long experienceId, ExperienceDto updatedExperience);

}

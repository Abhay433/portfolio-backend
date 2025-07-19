package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.model.Education;

public interface EducationService {
	
	public List<Education> getEducationByUserId(Long id);
	
	public void  addEducation(EducationDto educationDto, Long id);

	public void deleteEducationByUserIdAndEducationId(Long userId, Long deleteEducationId);
	
	public List<Education> updateEducationByUserId(Long userId,List<EducationDto> updatedEducationDto);

}

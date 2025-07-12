package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.repo.EducationRepository;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	EducationRepository educationrepository;

	public List<Education> getEducationByUserId(Long id){
		
		List<Education> userEducation =  educationrepository.findByUser_Id(id);
		
		return userEducation;
	}
}

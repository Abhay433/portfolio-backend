package com.portfolio.portfoliogenerator.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.repo.EducationRepository;
import com.portfolio.portfoliogenerator.repo.ExperienceRepository;
import com.portfolio.portfoliogenerator.repo.ProjectRepository;
import com.portfolio.portfoliogenerator.repo.SkillRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	

	@Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public boolean doesProfileExist(Long userId) {
    	
    	
        boolean hasEducation = educationRepository.existsByUser_Id(userId);
        boolean hasExperience = experienceRepository.existsByUser_Id(userId);
        boolean hasSkill = skillRepository.existsByUser_Id(userId);
        boolean hasProject = projectRepository.existsByUser_Id(userId);

        return  hasEducation || hasExperience || hasSkill || hasProject;
    }
    
    
}


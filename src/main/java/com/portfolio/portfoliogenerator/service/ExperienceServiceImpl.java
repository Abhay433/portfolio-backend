package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.ExperienceRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;



@Service
public class ExperienceServiceImpl implements ExperienceService {
	
	@Autowired
	ExperienceRepository experienceRepository;
	

    @Autowired
    private UserRepository userRepository;
	
	public List<Experience> getExperienceByUserId(Long id){
		
		List<Experience> experience=experienceRepository.findByUser_id(id);
		
		return experience;
		
	}
	
	@Override
    public void addExperience(ExperienceDto experienceDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Experience exp = new Experience();
        exp.setJobTitle(experienceDto.getJobTitle());
        exp.setCompany(experienceDto.getCompany());
        exp.setStartDate(experienceDto.getStartDate());
        exp.setEndDate(experienceDto.getEndDate());
        exp.setDescription(experienceDto.getDescription());
        exp.setUser(user); // 👈 Link to user

        experienceRepository.save(exp);
    }

}

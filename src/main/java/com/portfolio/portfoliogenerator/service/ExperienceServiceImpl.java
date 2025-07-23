package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.ExperienceRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.util.Capitalizer;



@Service
public class ExperienceServiceImpl implements ExperienceService {
	@Autowired
	Capitalizer capitalizer;
	
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
        
        exp.setJobTitle(capitalizer.capitalise(experienceDto.getJobTitle()));
        exp.setCompany(capitalizer.capitalise(experienceDto.getCompany()));
        exp.setStartDate(experienceDto.getStartDate());
        exp.setEndDate(experienceDto.getEndDate());
        exp.setDescription(capitalizer.capitalise(experienceDto.getDescription()));
        exp.setUser(user); // 👈 Link to user

        experienceRepository.save(exp);
    }
	
	@Override
    public void deleteExperienceByUserIdAndExperienceId(Long userId, Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
            .orElseThrow(() -> new RuntimeException("Experience not found with ID: " + experienceId));

        if (!experience.getUser().getId().equals(userId)) {
            throw new RuntimeException("This experience doesn't belong to the given user.");
        }

        experienceRepository.deleteById(experienceId);
    }
	
	@Override
	public List<Experience> updateExperienceByUserId(Long userId, List< ExperienceDto> updatedExperience) {
	   List< Experience> existingExperience = experienceRepository.findByUser_id(userId);

	        
	    if (existingExperience.size() != updatedExperience.size()) {
	        throw new RuntimeException("Experience record does not belong to user with ID: " + userId);
	    }

	 // Update each experience
	    for (int i = 0; i < existingExperience.size(); i++) {
	        Experience experience = existingExperience.get(i);
	        ExperienceDto dto = updatedExperience.get(i);

	        if (dto.getJobTitle() != null) experience.setJobTitle(capitalizer.capitalise(dto.getJobTitle()));
	        if (dto.getCompany() != null) experience.setCompany(capitalizer.capitalise(dto.getCompany()));
	        if (dto.getStartDate() != null) experience.setStartDate(dto.getStartDate());
	        if (dto.getEndDate() != null) experience.setEndDate(dto.getEndDate());
	        if (dto.getDescription() != null) experience.setDescription(capitalizer.capitalise(dto.getDescription()));
	    }

	    return experienceRepository.saveAll(existingExperience);
	}

}

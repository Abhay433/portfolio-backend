package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.ExperienceRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.util.Capitalizer;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ExperienceServiceImpl implements ExperienceService {
	@Autowired
	Capitalizer capitalizer;
	
	@Autowired
	ExperienceRepository experienceRepository;
	

    @Autowired
    private UserRepository userRepository;
	
	public List<Experience> getExperienceByUserId(Long id){
		
		List<Experience> experience=experienceRepository.findByUser_Id(id);
		
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
	@Transactional 
	public List<Experience> updateExperienceByUserId(Long userId, List<ExperienceDto> updatedExperience) {
	    System.out.println("🔧 Starting updateExperienceByUserId for userId: " + userId);

	    try {
	        System.out.println("🧹 Deleting existing experiences for user...");
	        experienceRepository.deleteByUser_Id(userId);
	    } catch (Exception e) {
	        System.out.println("❌ Failed to delete experiences: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }

	    List<Experience> newExperiences = new ArrayList<>();

	    for (ExperienceDto dto : updatedExperience) {
	        try {
	            System.out.println("📦 Processing DTO: " + dto);

	            Experience experience = new Experience();
	            experience.setJobTitle(capitalizer.capitalise(dto.getJobTitle()));
	            experience.setCompany(capitalizer.capitalise(dto.getCompany()));
	            experience.setStartDate(dto.getStartDate());
	            experience.setEndDate(dto.getEndDate());
	            experience.setDescription(capitalizer.capitalise(dto.getDescription()));

	            // Get user
	            User user = userRepository.findById(userId)
	                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
	            experience.setUser(user);

	            newExperiences.add(experience);
	        } catch (Exception e) {
	            System.out.println("❌ Error processing DTO: " + dto);
	            e.printStackTrace();
	            throw e;
	        }
	    }

	    try {
	        System.out.println("💾 Saving all new experiences...");
	        return experienceRepository.saveAll(newExperiences);
	    } catch (Exception e) {
	        System.out.println("❌ Failed to save experiences: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
	}



}

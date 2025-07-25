package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.EducationRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.util.Capitalizer;

import jakarta.transaction.Transactional;

@Service
public class EducationServiceImpl implements EducationService {
	
	@Autowired
	Capitalizer capitalizer;

	@Autowired
	EducationRepository educationRepository;
	
	@Autowired
	UserRepository userRepository;

	public List<Education> getEducationByUserId(Long id){
		
		List<Education> userEducation =  educationRepository.findByUser_Id(id);
		
		return userEducation;
	}
	
	@Override
	 public void  addEducation (EducationDto educationdto, Long id){
		 
		User user= userRepository.findById(id)
		 .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		
		System.out.println(user);

	        // Step 2: Create Education
	        Education edu = new Education();
	        edu.setDegree(capitalizer.capitalise(educationdto.getDegree()));
	        edu.setInstitution(capitalizer.capitalise(educationdto.getInstitution()));
	        edu.setStartYear(educationdto.getStartYear());
	        edu.setEndYear(educationdto.getEndYear());
	        edu.setUser(user);
	        
	       // 👈 Important step to link user
	        System.out.println(edu);
	        

	        // Step 3: Save
	        educationRepository.save(edu);
	        
	        System.out.println(user); 
	 }
	
	public void deleteEducationByUserIdAndEducationId(Long userId, Long educationId) {
	    Education education = educationRepository.findById(educationId)
	        .orElseThrow(() -> new RuntimeException("Education not found with ID: " + educationId));

	    if (!education.getUser().getId().equals(userId)) {
	        throw new RuntimeException("This education doesn't belong to the given user.");
	    }

	    educationRepository.deleteById(educationId);
	}
	
	@Transactional
	@Override
	public List<Education> updateEducationByUserId(Long userId, List<EducationDto> educationDtoList) {
	    Optional<User> userOptional = userRepository.findById(userId);
	    if (!userOptional.isPresent()) {
	        throw new RuntimeException("User not found with ID: " + userId);
	    }
	    User user = userOptional.get();

	    try {
	        System.out.println("🧹 Deleting existing experiences for user...");
	        educationRepository.deleteByUser_Id(userId);
	    } catch (Exception e) {
	        System.out.println("❌ Failed to delete experiences: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
	    List<Education> newEducations = new ArrayList<>();
	    for (EducationDto dto : educationDtoList) {
	        Education edu = new Education();
	        edu.setUser(user);
	        edu.setDegree(capitalizer.capitalise(dto.getDegree()));
	        edu.setInstitution(capitalizer.capitalise(dto.getInstitution()));
	        edu.setStartYear(dto.getStartYear());
	        edu.setEndYear(dto.getEndYear());
	        newEducations.add(edu);
	    }

	    return educationRepository.saveAll(newEducations);
	}



}




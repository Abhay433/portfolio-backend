package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		

	    // 🔐 Get currently authenticated user's email
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInEmail = authentication.getName();
	    
	   
		 
		User user= userRepository.findById(id)
		 .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		
		 // 🔒 Ensure logged-in user matches the ID provided
	    if (!user.getEmail().equals(loggedInEmail)) {
	        throw new AccessDeniedException("❌ You are not authorized to add education for this user.");
	    }
		
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
		
		// 🔐 Get currently authenticated user
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInEmail = authentication.getName();

	    User loggedInUser = userRepository.findByEmail(loggedInEmail)
	            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

	    // 🔒 Check that logged-in user is allowed to delete this education
	    if (!loggedInUser.getId().equals(userId)) {
	        throw new AccessDeniedException("❌ You are not authorized to delete this education.");
	    }
	    
	    Education education = educationRepository.findById(educationId)
	        .orElseThrow(() -> new RuntimeException("Education not found with ID: " + educationId));

	    if (!education.getUser().getId().equals(userId)) {
	        throw new RuntimeException("This education doesn't belong to the given user.");
	    }

	    educationRepository.deleteById(educationId);
	}
	
	@Transactional
	@Override
	public List<Education> updateEducationByUserId(Long userId, List<EducationDto> educationDtoList) { // 🔐 Get authenticated user
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInEmail = authentication.getName();

	    User loggedInUser = userRepository.findByEmail(loggedInEmail)
	            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

	    // 🔒 Ownership check
	    if (!loggedInUser.getId().equals(userId)) {
	        throw new AccessDeniedException("❌ You are not authorized to update this user's education.");
	    }

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
	        edu.setUser(loggedInUser);
	        edu.setDegree(capitalizer.capitalise(dto.getDegree()));
	        edu.setInstitution(capitalizer.capitalise(dto.getInstitution()));
	        edu.setStartYear(dto.getStartYear());
	        edu.setEndYear(dto.getEndYear());
	        newEducations.add(edu);
	    }

	    return educationRepository.saveAll(newEducations);
	}



}




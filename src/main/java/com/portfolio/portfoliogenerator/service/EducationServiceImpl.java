package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.EducationRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;

@Service
public class EducationServiceImpl implements EducationService {

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
	        edu.setDegree(educationdto.getDegree());
	        edu.setInstitution(educationdto.getInstitution());
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
	
	@Override
	public Education updateEducationByUserId(Long userId, Long educationId, EducationDto updatedEducationDto) {
	    Education existingEdu = educationRepository.findById(educationId)
	        .orElseThrow(() -> new RuntimeException("Education not found"));

	    if (!existingEdu.getUser().getId().equals(userId)) {
	        throw new RuntimeException("This education record does not belong to the user");
	    }

	    if (updatedEducationDto.getDegree() != null) {
	        existingEdu.setDegree(updatedEducationDto.getDegree());
	    }

	    if (updatedEducationDto.getInstitution() != null) {
	        existingEdu.setInstitution(updatedEducationDto.getInstitution());
	    }

	    if (updatedEducationDto.getStartYear() != null) {
	        existingEdu.setStartYear(updatedEducationDto.getStartYear());
	    }

	    if (updatedEducationDto.getEndYear() != null) {
	        existingEdu.setEndYear(updatedEducationDto.getEndYear());
	    }

	    educationRepository.save(existingEdu);
	    return existingEdu;
	}
}




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
	public List<Education> updateEducationByUserId(Long userId, List<EducationDto> updatedEducationDtoList) {
	    List<Education> existingEducations = educationRepository.findByUser_Id(userId);

	    if (existingEducations.isEmpty()) {
	        throw new RuntimeException("No education records found for user ID: " + userId);
	    }

	    if (existingEducations.size() != updatedEducationDtoList.size()) {
	        throw new RuntimeException("Mismatch between existing and incoming education record count");
	    }

	    for (int i = 0; i < existingEducations.size(); i++) {
	        Education education = existingEducations.get(i);
	        EducationDto dto = updatedEducationDtoList.get(i);

	        if (dto.getDegree() != null) education.setDegree(dto.getDegree());
	        if (dto.getInstitution() != null) education.setInstitution(dto.getInstitution());
	        if (dto.getStartYear() != null) education.setStartYear(dto.getStartYear());
	        if (dto.getEndYear() != null) education.setEndYear(dto.getEndYear());
	    }

	    return educationRepository.saveAll(existingEducations);
	}

}




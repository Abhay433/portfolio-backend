package com.portfolio.portfoliogenerator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.portfoliogenerator.dto.EducationDto;
import com.portfolio.portfoliogenerator.dto.ExperienceDto;
import com.portfolio.portfoliogenerator.dto.ProjectDto;
import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.dto.UserBasicDto;
import com.portfolio.portfoliogenerator.dto.UserDto;
import com.portfolio.portfoliogenerator.dto.UserSearchDto;
import com.portfolio.portfoliogenerator.model.Education;
import com.portfolio.portfoliogenerator.model.Experience;
import com.portfolio.portfoliogenerator.model.Project;
import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.security.SecurityUtil;
import com.portfolio.portfoliogenerator.util.Capitalizer;

import jakarta.transaction.Transactional;



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	Capitalizer capitalizer;

	@Autowired
	UserRepository userRepository;
	
	private final String UPLOAD_DIR = "uploadsProfile/";



	@Override
	public List<UserBasicDto> getAllUsers() {
	    return userRepository.findAll().stream()
	        .map(user -> {
	            UserBasicDto dto = new UserBasicDto();
	            dto.setFullName(user.getFullName());
	            dto.setEmail(user.getEmail());
	            dto.setPhone(user.getPhone());
	            dto.setAboutMe(user.getAboutMe());
	            dto.setAddress(user.getAddress());
	            return dto;
	        })
	        .collect(Collectors.toList());
	}


	
	@Override
	public void deleteUserById(Long id) {
		

	    // Get current logged-in user's email
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserEmail = auth.getName(); // usually email or username
	    

	    User currentUser = userRepository.findByEmail(currentUserEmail)
	        .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
	    // Check if the user trying to delete is the owner of the account
	    if (!currentUser.getId().equals(id)) {
	        throw new AccessDeniedException("❌ You are not authorized to delete this user.");
	    }
	    
	    if (!userRepository.existsById(id)) {
	        throw new RuntimeException("User not found with id: " + id);
	    }

	    userRepository.deleteById(id);
	}
	
	@Override
	public UserDto getUserById(Long id) {
		
		User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		
		UserDto userDto = new UserDto();
		 
		userDto.setFullName(user.getFullName());
	    userDto.setEmail(user.getEmail());
	    userDto.setPhone(user.getPhone());
	    userDto.setAboutMe(user.getAboutMe());
	    userDto.setAddress(user.getAddress());
	    userDto.setProfileImageUrl(user.getProfileImageUrl());
		
	    List<EducationDto> eduDtos = new ArrayList<>();
	    for (Education edu : user.getEducations()) {
	        EducationDto dto = new EducationDto();
	        dto.setDegree(edu.getDegree());
	        dto.setInstitution(edu.getInstitution());
	        dto.setStartYear(edu.getStartYear());
	        dto.setEndYear(edu.getEndYear());
	        eduDtos.add(dto);
	    }
	    userDto.setEducations(eduDtos);
	    
	    List<ExperienceDto> expDto = new ArrayList<>();
	    for (Experience exp : user.getExperiences()) {
	    	ExperienceDto dto = new ExperienceDto();
	        dto.setJobTitle(exp.getJobTitle());
	        dto.setCompany(exp.getCompany());
	        dto.setDescription(exp.getDescription());
	        dto.setEndDate(exp.getEndDate());
	        dto.setStartDate(exp.getStartDate());
	        expDto.add(dto);
	    }
	    userDto.setExperiences(expDto);
	    
	    List<SkillDto> skillDtos = new ArrayList<>();
	    for (Skill skill : user.getSkills()) {
	        SkillDto dto = new SkillDto();
	        dto.setName(skill.getName());
	        dto.setLevel(skill.getLevel());
	        skillDtos.add(dto);
	    }
	    userDto.setSkills(skillDtos);

	    // Similarly for Projects
	    List<ProjectDto> projectDtos = new ArrayList<>();
	    for (Project proj : user.getProjects()) {
	        ProjectDto dto = new ProjectDto();
	        dto.setTitle(proj.getTitle());
	        dto.setDescription(proj.getDescription());
	        dto.setTechnologiesUsed(proj.getTechnologiesUsed());
	        dto.setProjectUrl(proj.getProjectUrl());
	        projectDtos.add(dto);
	    }
	    userDto.setProjects(projectDtos);
	    
	    List<ExperienceDto> experienceDto = new ArrayList<>();
	    for (Experience exper : user.getExperiences()) {
	        ExperienceDto dto = new ExperienceDto();
	        dto.setJobTitle(exper.getJobTitle());
	        dto.setCompany(exper.getCompany());
	        dto.setStartDate(exper.getStartDate());
	        dto.setEndDate(exper.getEndDate());
	        dto.setDescription(exper.getDescription());
	        
	        experienceDto.add(dto);
	    }
	    userDto.setExperiences(experienceDto);


	    return userDto;
	}
	
	@Transactional
	@Override
	public User updateUserById(Long id, UserDto userDto) {
	    User existingUser = userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found by id: " + id));

	    // ✅ Check ownership
	    String loggedInEmail = SecurityUtil.getCurrentUserEmail();
	    if (!existingUser.getEmail().equals(loggedInEmail)) {
	        throw new AccessDeniedException("⛔ You are not allowed to edit this user.");
	    }
	
	    // Update basic fields
	    existingUser.setFullName(capitalizer.capitalise(userDto.getFullName()));
	    existingUser.setEmail(userDto.getEmail());
	    existingUser.setPhone(userDto.getPhone());
	    existingUser.setAboutMe(capitalizer.capitalise(userDto.getAboutMe()));
	    existingUser.setAddress(capitalizer.capitalise(userDto.getAddress()));
	
	    // Update education (if provided)
	    if (userDto.getEducations() != null) {
	        List<Education> educationList = new ArrayList<>();
	        for (EducationDto eduDto : userDto.getEducations()) {
	            Education edu = new Education();
	            edu.setDegree(capitalizer.capitalise(eduDto.getDegree()));
	            edu.setInstitution(capitalizer.capitalise(eduDto.getInstitution()));
	            edu.setStartYear(eduDto.getStartYear());
	            edu.setEndYear(eduDto.getEndYear());
	            edu.setUser(existingUser);
	            educationList.add(edu);
	        }
	        existingUser.setEducations(educationList);
	    }
	
	    // Update experiences (if provided)
	    if (userDto.getExperiences() != null) {
	        List<Experience> experienceList = new ArrayList<>();
	        for (ExperienceDto expDto : userDto.getExperiences()) {
	            Experience exp = new Experience();
	            exp.setJobTitle(capitalizer.capitalise(expDto.getJobTitle()));
	            exp.setCompany(capitalizer.capitalise(expDto.getCompany()));
	            exp.setStartDate(expDto.getStartDate());
	            exp.setEndDate(expDto.getEndDate());
	            exp.setDescription(capitalizer.capitalise(expDto.getDescription()));
	            exp.setUser(existingUser);
	            experienceList.add(exp);
	        }
	        existingUser.setExperiences(experienceList);
	    }
	
	    // Update skills (if provided)
	    if (userDto.getSkills() != null) {
	        List<Skill> skillList = new ArrayList<>();
	        for (SkillDto skillDto : userDto.getSkills()) {
	            Skill skill = new Skill();
	            skill.setName(capitalizer.capitalise(skillDto.getName()));
	            skill.setLevel(capitalizer.capitalise(skillDto.getLevel()));
	            skill.setUser(existingUser);
	            skillList.add(skill);
	        }
	        existingUser.setSkills(skillList);
	    }
	
	    // Update projects (if provided)
	    if (userDto.getProjects() != null) {
	        List<Project> projectList = new ArrayList<>();
	        for (ProjectDto projDto : userDto.getProjects()) {
	            Project proj = new Project();
	            proj.setTitle(capitalizer.capitalise(projDto.getTitle()));
	            proj.setDescription(capitalizer.capitalise(projDto.getDescription()));
	            proj.setTechnologiesUsed(capitalizer.capitalise(projDto.getTechnologiesUsed()));
	            proj.setProjectUrl(projDto.getProjectUrl());
	            proj.setUser(existingUser);
	            projectList.add(proj);
	        }
	        existingUser.setProjects(projectList);
	    }
	
	    return userRepository.save(existingUser);
	}


	@Override
    public void uploadProfileImage(Long userId, MultipartFile file) {
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            

            String loggedInEmail = SecurityUtil.getCurrentUserEmail();
            
            // 🔐 Ownership check
            if (!user.getEmail().equalsIgnoreCase(loggedInEmail)) {
                throw new AccessDeniedException("⛔ You are not authorized to modify this user's profile image.");
            }


            // Create uploads directory if not exists
            File uploadFolder = new File(UPLOAD_DIR);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Save file to disk
            Files.write(filePath, file.getBytes());

            // Save URL/path to DB
            user.setProfileImageUrl("/" + UPLOAD_DIR + fileName);
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }
		
	
	@Override
public User updateUserProfileWithImage(Long userId, UserDto userDto, MultipartFile file) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    // BASIC INFO with null checks
    if (userDto.getPhone() != null) user.setPhone(userDto.getPhone());
    if (userDto.getAboutMe() != null) user.setAboutMe(capitalizer.capitalise(userDto.getAboutMe()));
    if (userDto.getAddress() != null) user.setAddress(capitalizer.capitalise(userDto.getAddress()));

    // ✅ Do NOT update email here. Use original email from login.

    // IMAGE upload
    if (file != null && !file.isEmpty()) {
        try {
            String uploadDir = "uploadsProfile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            user.setProfileImageUrl("/" + uploadDir + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    // EDUCATION
    if (userDto.getEducations() != null && !userDto.getEducations().isEmpty()) {
        List<Education> educationList = new ArrayList<>();
        for (EducationDto eduDto : userDto.getEducations()) {
            Education edu = new Education();
            if (eduDto.getDegree() != null) edu.setDegree(capitalizer.capitalise(eduDto.getDegree()));
            if (eduDto.getInstitution() != null) edu.setInstitution(capitalizer.capitalise(eduDto.getInstitution()));
            if (eduDto.getStartYear() != null) edu.setStartYear(eduDto.getStartYear());
            if (eduDto.getEndYear() != null) edu.setEndYear(eduDto.getEndYear());
            edu.setUser(user);
            educationList.add(edu);
        }
        user.setEducations(educationList);
    }

    // EXPERIENCE
    if (userDto.getExperiences() != null && !userDto.getExperiences().isEmpty()) {
        List<Experience> experienceList = new ArrayList<>();
        for (ExperienceDto expDto : userDto.getExperiences()) {
            Experience exp = new Experience();
            if (expDto.getJobTitle() != null) exp.setJobTitle(capitalizer.capitalise(expDto.getJobTitle()));
            if (expDto.getCompany() != null) exp.setCompany(capitalizer.capitalise(expDto.getCompany()));
            if (expDto.getStartDate() != null) exp.setStartDate(expDto.getStartDate());
            if (expDto.getEndDate() != null) exp.setEndDate(expDto.getEndDate());
            if (expDto.getDescription() != null) exp.setDescription(capitalizer.capitalise(expDto.getDescription()));
            exp.setUser(user);
            experienceList.add(exp);
        }
        user.setExperiences(experienceList);
    }

    // SKILLS
    if (userDto.getSkills() != null && !userDto.getSkills().isEmpty()) {
        List<Skill> skillList = new ArrayList<>();
        for (SkillDto skillDto : userDto.getSkills()) {
            Skill skill = new Skill();
            if (skillDto.getName() != null) skill.setName(capitalizer.capitalise(skillDto.getName()));
            if (skillDto.getLevel() != null) skill.setLevel(capitalizer.capitalise(skillDto.getLevel()));
            skill.setUser(user);
            skillList.add(skill);
        }
        user.setSkills(skillList);
    }

    // PROJECTS
    if (userDto.getProjects() != null && !userDto.getProjects().isEmpty()) {
        List<Project> projectList = new ArrayList<>();
        for (ProjectDto projDto : userDto.getProjects()) {
            Project proj = new Project();
            if (projDto.getTitle() != null) proj.setTitle(capitalizer.capitalise(projDto.getTitle()));
            if (projDto.getDescription() != null) proj.setDescription(capitalizer.capitalise(projDto.getDescription()));
            if (projDto.getTechnologiesUsed() != null) proj.setTechnologiesUsed(capitalizer.capitalise(projDto.getTechnologiesUsed()));
            if (projDto.getProjectUrl() != null) proj.setProjectUrl(projDto.getProjectUrl());
            proj.setUser(user);
            projectList.add(proj);
        }
        user.setProjects(projectList);
    }

    return userRepository.save(user);
}




	@Override
	public User updateUserBasicDetails(Long id, UserBasicDto userBasicDto) {

	    User existingUser = userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	    // ✅ Check ownership
	    String loggedInEmail = SecurityUtil.getCurrentUserEmail();
	    if (!existingUser.getEmail().equals(loggedInEmail)) {
	        throw new AccessDeniedException("⛔ You are not allowed to edit this user.");
	    }
	   

	    if (userBasicDto.getFullName() != null) {
	    	existingUser.setFullName(capitalizer.capitalise(userBasicDto.getFullName()));
	    }

	    if (userBasicDto.getPhone() != null) {
	    	existingUser.setPhone(capitalizer.capitalise(userBasicDto.getPhone()));
	    }

	    if (userBasicDto.getEmail() != null) {
	    	existingUser.setEmail(userBasicDto.getEmail());
	    }

	    if (userBasicDto.getAboutMe() != null) {
	    	existingUser.setAboutMe(capitalizer.capitalise(userBasicDto.getAboutMe()));
	    }

	    if (userBasicDto.getAddress() != null) {
	    	existingUser.setAddress(capitalizer.capitalise(userBasicDto.getAddress()));
	    }

	    userRepository.save(existingUser);
	    return existingUser;
	}
	
	

	public List<UserSearchDto> findByFullNameOrByEmail(String fullName, String eMail) {
	    List<User> users = userRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(fullName, eMail);
	    List<UserSearchDto> result = new ArrayList<>();

	    for (User user : users) {
	    	UserSearchDto dto = new UserSearchDto();
	    	dto.setId(user.getId());
	        dto.setFullName(user.getFullName());
	        dto.setEmail(user.getEmail());
	        result.add(dto);
	    }

	    return result;
	}

	


}
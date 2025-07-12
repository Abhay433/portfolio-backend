package com.portfolio.portfoliogenerator.dto;

import java.util.List;

public class UserDto {
	
    private String fullName;
    
    private String email;
    
    private String phone;
    
    private String aboutMe;
    
    private String address;
    
    

    private List<EducationDto> educations;
    
    private List<ExperienceDto> experiences;
    
    private List<SkillDto> skills;
    
    private List<ProjectDto> projects;
    
	public String getFullName() {
		return fullName;
	}
	
	
	
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<EducationDto> getEducations() {
		return educations;
	}
	public void setEducations(List<EducationDto> educations) {
		this.educations = educations;
	}
	public List<ExperienceDto> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<ExperienceDto> experiences) {
		this.experiences = experiences;
	}
	public List<SkillDto> getSkills() {
		return skills;
	}
	public void setSkills(List<SkillDto> skills) {
		this.skills = skills;
	}
	public List<ProjectDto> getProjects() {
		return projects;
	}
	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}

    // Getters & Setters
}

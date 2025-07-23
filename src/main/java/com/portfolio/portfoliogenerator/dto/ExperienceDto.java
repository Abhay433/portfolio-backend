package com.portfolio.portfoliogenerator.dto;


public class ExperienceDto {
    @Override
	public String toString() {
		return "ExperienceDto [jobTitle=" + jobTitle + ", company=" + company + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", description=" + description + ", getJobTitle()=" + getJobTitle()
				+ ", getCompany()=" + getCompany() + ", getStartDate()=" + getStartDate() + ", getEndDate()="
				+ getEndDate() + ", getDescription()=" + getDescription() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	private String jobTitle;
    private String company;
    private String startDate;
    private String endDate;
    private String description;
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    // Getters & Setters
}

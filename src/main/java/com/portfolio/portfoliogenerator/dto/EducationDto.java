package com.portfolio.portfoliogenerator.dto;



public class EducationDto {
	
	
    private String degree;
    
    private String institution;
    
    private String startYear;
    
    private String endYear;
    
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	@Override
	public String toString() {
		return "EducationDto [degree=" + degree + ", institution=" + institution + ", startYear=" + startYear
				+ ", endYear=" + endYear + ", getDegree()=" + getDegree() + ", getInstitution()=" + getInstitution()
				+ ", getStartYear()=" + getStartYear() + ", getEndYear()=" + getEndYear() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

	

}

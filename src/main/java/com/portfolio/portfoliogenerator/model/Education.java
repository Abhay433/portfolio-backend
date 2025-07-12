package com.portfolio.portfoliogenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Education {

    @Override
	public String toString() {
		return "Education [id=" + id + ", degree=" + degree + ", institution=" + institution + ", startYear="
				+ startYear + ", endYear=" + endYear + ", user=" + user + ", getId()=" + getId() + ", getDegree()="
				+ getDegree() + ", getInstitution()=" + getInstitution() + ", getStartYear()=" + getStartYear()
				+ ", getEndYear()=" + getEndYear() + ", getUser()=" + getUser() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    
    private String institution;
    
    private Integer startYear;
    
    private Integer endYear;
   
    
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    // Getters & Setters
}

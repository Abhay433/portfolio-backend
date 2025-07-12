package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.Education;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {

	List<Education> findByUser_Id(Long userId);
	
	

}

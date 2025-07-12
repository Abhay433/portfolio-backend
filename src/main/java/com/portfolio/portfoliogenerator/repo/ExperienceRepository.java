package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	
	
}

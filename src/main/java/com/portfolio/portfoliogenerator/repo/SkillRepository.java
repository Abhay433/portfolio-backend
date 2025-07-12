package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.Skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	
	List<Skill> findByUser_id(Long id);
}

package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}

package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

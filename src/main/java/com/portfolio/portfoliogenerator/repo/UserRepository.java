package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	
}




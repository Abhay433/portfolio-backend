package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
		List<User> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName,String eMail);
		
		Optional<User> findByEmail(String email);
		

}




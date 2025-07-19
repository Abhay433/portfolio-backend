package com.portfolio.portfoliogenerator.repo;

import com.portfolio.portfoliogenerator.dto.UserDto;
import com.portfolio.portfoliogenerator.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<UserDto> findByFullNameOrByEmail(String fullName,String eMail);
	
}




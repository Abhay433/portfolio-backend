package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.repo.SkillRepository;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	SkillRepository skillRepository;
	
	public List<Skill> getSkillByUserId(Long id){
		
		List<Skill> skill=skillRepository.findByUser_id(id);
		
		return skill;
		
		
		
	}

}

package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.model.Skill;

public interface SkillService {

	
	public List<Skill> getSkillByUserId(Long id);

}

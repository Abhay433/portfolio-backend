package com.portfolio.portfoliogenerator.service;

import java.util.List;

import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.model.Skill;

public interface SkillService {

	
	public List<Skill> getSkillByUserId(Long id);
	
	public void addSkill(SkillDto skilldto,Long id);
	
	public void deleteSkillByUserIdAndSkillId(Long userId, Long skillId);
	
	List<Skill> updateSkillByUserIdAndSkillId(Long userId,  List<SkillDto>  skillDto);
	

}

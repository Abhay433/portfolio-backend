package com.portfolio.portfoliogenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.SkillRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	SkillRepository skillRepository;

	  @Autowired
	    private UserRepository userRepository;
	
	
	
	public List<Skill> getSkillByUserId(Long id){
		
		List<Skill> skill=skillRepository.findByUser_id(id);
		
		return skill;
	}
	
	@Override
    public void addSkill(SkillDto skillDto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        skill.setLevel(skillDto.getLevel());
        skill.setUser(user); // 👈 Set the user

        skillRepository.save(skill);
    }
	
	@Override
    public void deleteSkillByUserIdAndSkillId(Long userId, Long skillId) {
        Skill skill = skillRepository.findById(skillId)
            .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + skillId));

        if (!skill.getUser().getId().equals(userId)) {
            throw new RuntimeException("This skill doesn't belong to the given user.");
        }

        skillRepository.deleteById(skillId);
    }

	@Override
	public List<Skill> updateSkillByUserIdAndSkillId(Long userId, List<SkillDto> skillDtoList) {
	    List<Skill> existingSkills = skillRepository.findByUser_id(userId);

	    if (existingSkills.size() != skillDtoList.size()) {
	        throw new RuntimeException("Mismatch between existing and incoming skill count");
	    }

	    for (int i = 0; i < existingSkills.size(); i++) {
	        Skill skill = existingSkills.get(i);
	        SkillDto dto = skillDtoList.get(i);

	        if (dto.getName() != null) skill.setName(dto.getName());
	        if (dto.getLevel() != null) skill.setLevel(dto.getLevel());
	    }

	    return skillRepository.saveAll(existingSkills);
	}
}

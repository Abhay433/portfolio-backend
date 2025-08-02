package com.portfolio.portfoliogenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.portfolio.portfoliogenerator.dto.SkillDto;
import com.portfolio.portfoliogenerator.model.Skill;
import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.SkillRepository;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import com.portfolio.portfoliogenerator.security.SecurityUtil;
import com.portfolio.portfoliogenerator.util.Capitalizer;

import jakarta.transaction.Transactional;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	Capitalizer capitalizer;
	
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
        		
        

        String loggedInEmail = SecurityUtil.getCurrentUserEmail();

        // 🔐 Ensure that the logged-in user is the owner
        if (!user.getEmail().equals(loggedInEmail)) {
            throw new AccessDeniedException("⛔ You are not allowed to add skills to this user's portfolio.");
        }

        Skill skill = new Skill();
        skill.setName(capitalizer.capitalise(skillDto.getName()));
        skill.setLevel(capitalizer.capitalise(skillDto.getLevel()));
        skill.setUser(user); // 👈 Set the user

        skillRepository.save(skill);
    }
	
	@Override
	public void deleteSkillByUserIdAndSkillId(Long userId, Long skillId) {
	    Skill skill = skillRepository.findById(skillId)
	        .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + skillId));

	    // ✅ Fetch the logged-in user's email
	    String loggedInEmail = SecurityUtil.getCurrentUserEmail();

	    // ✅ Fetch the actual owner of the skill
	    String skillOwnerEmail = skill.getUser().getEmail();

	    // ✅ Compare
	    if (!loggedInEmail.equals(skillOwnerEmail)) {
	        throw new AccessDeniedException("⛔ You are not allowed to delete this skill.");
	    }

	    skillRepository.deleteById(skillId);
	}


	@Transactional
	@Override
	public List<Skill> updateSkillByUserId(Long userId, List<SkillDto> skillDtoList) {
	    // Fetch user by ID
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
	    
	 // ✅ Get logged-in email
	    String loggedInEmail = SecurityUtil.getCurrentUserEmail();

	    // ✅ Check if logged-in user is the owner
	    if (!user.getEmail().equals(loggedInEmail)) {
	        throw new AccessDeniedException("⛔ You are not allowed to edit this portfolio.");
	    }

	    // Delete old skills linked to the user
	    skillRepository.deleteByUser_Id(userId);

	    List<Skill> updatedSkills = new ArrayList<>();

	    for (SkillDto dto : skillDtoList) {
	        Skill skill = new Skill();

	        // Attach the user to the skill
	        skill.setUser(user);

	        // Capitalize and set values safely
	        skill.setName(dto.getName() != null ? capitalizer.capitalise(dto.getName()) : null);
	        skill.setLevel(dto.getLevel() != null ? capitalizer.capitalise(dto.getLevel()) : null);

	        updatedSkills.add(skill);
	    }

	    return skillRepository.saveAll(updatedSkills);
	}

}

package com.portfolio.portfoliogenerator.service;

import com.portfolio.portfoliogenerator.model.PasswordResetToken;
import com.portfolio.portfoliogenerator.model.User;

public interface PasswordResetTokenService {
	
	public void createPasswordResetToken(User user, String token);
	

    public PasswordResetToken validatePasswordResetToken(String token);

}

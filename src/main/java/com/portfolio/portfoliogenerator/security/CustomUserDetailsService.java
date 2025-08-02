package com.portfolio.portfoliogenerator.security;

import com.portfolio.portfoliogenerator.model.User;
import com.portfolio.portfoliogenerator.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 🔹 This method is called by Spring Security during login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 🔸 Fetch user by email from DB
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get();

        // 🔸 Wrap into CustomUserDetails (so Spring Security understands it)
        return new CustomUserDetails(user);
    }
}

package com.portfolio.portfoliogenerator.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.portfolio.portfoliogenerator.model.User;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 🔹 Return user's authorities (roles)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    // 🔹 Return password (used internally by Spring Security)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 🔹 Return username (we'll use email as username)
    @Override
    public String getUsername() {
        return user.getEmail();  // we are treating email as username
    }

    // 🔹 Account is not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 🔹 Account is not locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 🔹 Credentials (password) are not expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 🔹 Account is enabled
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 🔸 Optionally: expose user object if needed later
    public User getUser() {
        return user;
    }
    
    public Long getId() {
        return user.getId();
    }
}

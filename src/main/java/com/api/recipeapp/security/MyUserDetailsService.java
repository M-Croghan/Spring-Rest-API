package com.api.recipeapp.security;


import com.api.recipeapp.model.User;
import com.api.recipeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Allows for loading of a specific user and related details
// UserDetailsService is a core interface in the Spring Security Framework
// Retrieves user's authorization & authentication information
@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);
        return new MyUserDetails(user);
    }
}

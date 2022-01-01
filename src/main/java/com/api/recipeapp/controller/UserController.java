package com.api.recipeapp.controller;

import com.api.recipeapp.model.User;
import com.api.recipeapp.model.UserProfile;
import com.api.recipeapp.model.request.LoginRequest;
import com.api.recipeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/auth/users")
public class UserController {
    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User userObject){
        LOGGER.info("Calling createUser method from Controller!");
        return userService.createUser(userObject);
    }

    // http://localhost:9092/auth/users/login
    // ResponseEntity: The object & status code
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

    // http://localhost:9092/auth/users/profile
    @PostMapping("/profile")
    public UserProfile createProfile(@RequestBody UserProfile user){
        LOGGER.info("Calling createProfile method from Controller!");
        return userService.createProfile(user);
    }

    // http://localhost:9092/auth/users/profile
    @PutMapping("/profile")
    public UserProfile updateProfile(@RequestBody UserProfile user){
        LOGGER.info("Calling updateProfile method from Controller!");
        return userService.updateProfile(user);
    }
}

package com.api.recipeapp.service;


import com.api.recipeapp.exception.InformationExistException;
import com.api.recipeapp.exception.InformationNotFoundException;
import com.api.recipeapp.model.User;
import com.api.recipeapp.model.UserProfile;
import com.api.recipeapp.model.request.LoginRequest;
import com.api.recipeapp.model.response.LoginResponse;
import com.api.recipeapp.repository.UserRepository;
import com.api.recipeapp.security.JWTUtils;
import com.api.recipeapp.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

// Business Logic
@Service
public class UserService {
    private UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User userOject){
        LOGGER.info("Calling createUser from service!");
        if (!userRepository.existsByEmailAddress(userOject.getEmailAddress())){
            // Retrieves user object password, encodes it, and sets / saves the password (Password Encryption)
            userOject.setPassword(passwordEncoder.encode(userOject.getPassword()));
            return userRepository.save(userOject);
        }
        else {
            throw new InformationExistException("User with email address " +
                    userOject.getEmailAddress() + " already exists!");
        }
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
    public User findUserByEmailAddress(String email){
        return userRepository.findUserByEmailAddress(email);
    }

   // TODO: Advanced Exception & Error Handling
   public UserProfile createProfile(UserProfile user) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
               .getPrincipal();
        Optional<User> userProfile = userRepository.findByIdAndEmailAddress(userDetails.getUser().getId(),
                userDetails.getUsername());
        if (userProfile.isPresent() && userProfile.get().getUserProfile() != null){
            throw new InformationExistException("This user has a profile!");
        }
        else{
            UserProfile newUser = new UserProfile(user.getFirstName(), user.getLastName(), user.getProfileDescription(),
                    userDetails.getUser());
            userProfile.get().setUserProfile(newUser);
            return userRepository.save(newUser);
        }
    }

    // TODO: Advanced Exception & Error Handling
    public UserProfile updateProfile(UserProfile user) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> userProfile = userRepository.findByIdAndEmailAddress(userDetails.getUser().getId(), userDetails.getUsername());
        if (userProfile.isPresent()){
            UserProfile profile = userProfile.get().getUserProfile();
            if (profile.getProfileDescription() != null){
                profile.setFirstName(user.getFirstName());
                profile.setLastName(user.getLastName());
                profile.setProfileDescription(user.getProfileDescription());
                return userRepository.save(profile);
            }
            else {
                throw new InformationNotFoundException("This user has no profile to update!");
            }
        }
        else{
            throw new InformationNotFoundException("This user has no profile to update!");
        }
    }
}

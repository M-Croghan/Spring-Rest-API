package com.api.recipeapp.repository;


import com.api.recipeapp.model.User;
import com.api.recipeapp.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // To Register User
    boolean existsByEmailAddress(String userEmailAddress);

    // To Login
    User findUserByEmailAddress(String userEmailAddress);

    Optional<User> findByIdAndEmailAddress(Long Id, String email);

    UserProfile save(UserProfile user);

    UserProfile save(Optional<User> userProfile);
}

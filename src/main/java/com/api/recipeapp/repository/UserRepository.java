package com.api.recipeapp.repository;


import com.api.recipeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // To Register User
    boolean existsByEmailAddress(String userEmailAddress);

    // To Login
    User findUserByEmailAddress(String userEmailAddress);
}

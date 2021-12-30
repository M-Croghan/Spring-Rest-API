package com.api.recipeapp.repository;


import com.api.recipeapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);

    List<Category> findByUserId(Long userId);

    Category findByUserIdAndName(Long userId, String categoryName);

    Category findByIdAndUserId(Long userId, Long CategoryId);

}

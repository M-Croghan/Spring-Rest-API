package com.api.recipeapp.repository;


import com.api.recipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoryId(Long categoryId);

    Recipe findByUserIdAndName(Long id, String categoryName);
}

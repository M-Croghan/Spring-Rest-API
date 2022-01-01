package com.api.recipeapp.controller;

import com.api.recipeapp.model.Category;
import com.api.recipeapp.model.Recipe;
import com.api.recipeapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

    private CategoryService categoryService;
    private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // http://localhost:9092/api/categories
    @GetMapping("/categories")
    public List<Category> getCategories() {
        LOGGER.info("calling getCategories method from controller");
        return categoryService.getCategories();
    }

    // http://localhost:9092/api/categories/1
    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        LOGGER.info("calling getCategory method from controller");
        return categoryService.getCategory(categoryId);
    }

    // http://localhost:9092/api/categories
    @PostMapping(path = "/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        LOGGER.info("calling createCategory method from controller");
        return categoryService.createCategory(categoryObject);
    }

    // http://localhost:9092/api/categories/1
    @PutMapping(path = "/categories/{categoryId}")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject) {
        LOGGER.info("calling updateCategory method from controller");
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    // http://localhost:9092/api/categories/10
    @DeleteMapping("/categories/{categoryId}")
    public Category deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        LOGGER.info("calling deleteCategory method from controller");
        return categoryService.deleteCategory(categoryId);
    }

    // http://localhost:9092/api/categories/10/recipes
    @PostMapping(path = "categories/{categoryId}/recipes")
    public Recipe createCategoryRecipe(
            @PathVariable(value = "categoryId") Long categoryId,
            @RequestBody Recipe recipeObject) {
        LOGGER.info("Calling createCategoryRecipe method from controller");
        return categoryService.createCategoryRecipe(categoryId, recipeObject);
    }

    // GET - All Recipes by Category ID & Recipe ID
    // http://localhost:9092/api/categories/10/recipes/1
    @GetMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Recipe getCategoryRecipe(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "recipeId") Long recipeId) {
        LOGGER.info("Calling getCategoryRecipe method from controller");
        return categoryService.getCategoryRecipe(categoryId, recipeId);
    }

    // GET - All Recipes by Category ID
    // http://localhost:9092/api/categories/10/recipes
    @GetMapping("/categories/{categoryId}/recipes")
    public List<Recipe> getCategoryRecipes(@PathVariable(value = "categoryId") Long categoryId) {
        LOGGER.info("Calling getCategoryRecipes method from controller");
        return categoryService.getCategoryRecipes(categoryId);
    }

    // UPDATE - Recipe
    // http://localhost:9092/api/categories/10/recipes/1
    @PutMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Recipe updateCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId,
                                       @PathVariable(value = "recipeId") Long recipeId,
                                       @RequestBody Recipe recipeObject) {
        LOGGER.info("Calling updateCategoryRecipe method from controller");
        return categoryService.updateCategoryRecipe(categoryId, recipeId, recipeObject);
    }

    // DELETE - Recipe
    // http://localhost:9092/api/categories/10/recipes/1
    @DeleteMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Optional<Recipe> deleteCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId,
                                                 @PathVariable(value = "recipeId") Long recipeId) {
        LOGGER.info("Calling deleteCategoryRecipe method from controller");
        return categoryService.deleteCategoryRecipe(categoryId, recipeId);
    }

    // http://localhost:9092/api/public-recipes/
    @GetMapping("/public-recipes")
    public List<Recipe> getPublicRecipes(){
        LOGGER.info("calling getPublicRecipes() method from CategoryController");
        return categoryService.getPublicRecipes();
    }
}
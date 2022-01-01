package com.api.recipeapp.service;

import com.api.recipeapp.exception.InformationExistException;
import com.api.recipeapp.exception.InformationNotFoundException;
import com.api.recipeapp.model.Category;
import com.api.recipeapp.model.Recipe;
import com.api.recipeapp.model.User;
import com.api.recipeapp.repository.CategoryRepository;
import com.api.recipeapp.repository.RecipeRepository;
import com.api.recipeapp.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

// This service is responsible for talking to the database
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());


    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    // GET: All Categories
    public List<Category> getCategories() {
        LOGGER.info("Calling getCategories() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<Category> categories = categoryRepository.findByUserId(userDetails.getUser().getId());
        if (categories.isEmpty()){
            throw new InformationNotFoundException("No categories found for user with ID: " + userDetails.getUser().getId());
        }
        else {
            return categories;
        }
    }

    // GET: Category by ID
    public Category getCategory(Long categoryId) {
        LOGGER.info("Calling getCategory() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category != null) {
            return category;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    // POST: New Category
    public Category createCategory(Category categoryObject) {
        LOGGER.info("Calling createCategory() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByUserIdAndName(userDetails.getUser().getId(),
                categoryObject.getName());
        if (category != null){
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        }
        else {
            categoryObject.setUser(userDetails.getUser());
            return categoryRepository.save(categoryObject);
        }
    }

    // PUT: Update Category
    public Category updateCategory(Long categoryId, Category categoryObject) {
        LOGGER.warning("Calling updateCategory() from CategoryService on category ID: " + categoryId);
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        // Find by ID
        if (category != null) {
            // Check the category name for match in the DB
            if (categoryObject.getName().equals(category.getName())) {
                LOGGER.warning("Category name is equal to database object name!");
                throw new InformationExistException("Category " + category.getName() + " already exists!");
            } else {
                // Find the category and update it with new information
                category.setName(categoryObject.getName());
                category.setDescription(categoryObject.getDescription());
                return categoryRepository.save(category);
            }
        } else {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found!");
        }
    }

    // DELETE: Delete Category
    public Category deleteCategory(Long categoryId) {
        LOGGER.warning("Calling deleteCategory() from CategoryService on category ID: " + categoryId);
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category != null){
            categoryRepository.deleteById(categoryId);
            return category;
        }
        else {
            throw new InformationNotFoundException("Category ID" + categoryId + " does not exist.");
        }
    }

    // GET: Create a recipe
    public Recipe createCategoryRecipe(Long categoryId, Recipe recipeObject){
        LOGGER.info("Calling createCategoryRecipe() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException(
                    "Category with the id: " + categoryId + " does not belong to this user / Category does not exist!");
        }
        Recipe recipe = recipeRepository.findByUserIdAndName(userDetails.getUser().getId(), recipeObject.getName());
        if (recipe != null) {
            throw new InformationExistException("Recipe with the name " + recipe.getName() + " already exists!");
        }
        recipeObject.setUser(userDetails.getUser());
        recipeObject.setCategory(category);
        return recipeRepository.save(recipeObject);
    }

    // GET: Get Recipe by ID
    public Recipe getCategoryRecipe(Long categoryId, Long recipeId){
        LOGGER.info("Calling getCategoryRecipe() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId()));
        if(category.isPresent()){
            Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId).stream().filter(
                    p -> p.getId().equals(recipeId)).findFirst();
            if(recipe.isEmpty()){
                throw new InformationNotFoundException("Recipe with ID " + recipeId + " not found!");
            }
            else{
                return recipe.get();
            }
        }
        else {
            throw new InformationNotFoundException("Category with ID " + categoryId + " not found!");
        }
    }

    // GET: Get All Recipes
    public List<Recipe> getCategoryRecipes(Long categoryId){
        LOGGER.info("Calling getCategoryRecipes method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("Category with id " + categoryId + " not found");
        } else {
            return category.getRecipeList();
        }
    }

    // PUT: Update Recipe
    public Recipe updateCategoryRecipe(Long categoryId, Long recipeId, Recipe recipeObject) {
        LOGGER.info("Calling getCategoryRecipe() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId());
        if (category != null){
            try {
                Recipe recipe = (recipeRepository.findByCategoryId(
                        categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst()).get();
                recipe.setName(recipeObject.getName());
                recipe.setPortions(recipeObject.getPortions());
                recipe.setIngredients(recipeObject.getIngredients());
                recipe.setSteps(recipeObject.getSteps());
                recipe.setTime(recipeObject.getTime());
                recipe.setIsPublic(recipeObject.getIsPublic());
                return recipeRepository.save(recipe);
            } catch (NoSuchElementException e) {
                throw new InformationNotFoundException("Recipe Not Found!");
            }
        }
        else{
            throw new InformationNotFoundException("Category Not Found!");
        }
    }

    // DELETE: Delete Recipe by ID
    public Optional<Recipe> deleteCategoryRecipe(Long categoryId, Long recipeId) {
        LOGGER.info("Calling deleteCategoryRecipe() method from CategoryService");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, userDetails.getUser().getId()));
        if (category.isPresent()) {
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);
            if (recipe.isPresent()) {
                recipeRepository.deleteById(recipeId);
                return recipe;
            } else {
                throw new InformationExistException("Recipe does not exist with id " + recipeId);
            }
        } else {
            throw new InformationExistException("Category does not exist with id " + categoryId);
        }
    }

    //TODO: Return all public recipes!
    public List<Recipe> getPublicRecipes() {
        LOGGER.info("calling getPublicRecipes() method from CategoryController");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        if (user != null){
            return recipeRepository.findAll();
        }
        throw new InformationNotFoundException("No Recipes Found!");

    }
}

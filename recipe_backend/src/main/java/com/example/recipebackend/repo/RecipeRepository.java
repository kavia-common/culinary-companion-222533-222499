package com.example.recipebackend.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipebackend.domain.Recipe;

/**
 * Mongo repository for recipes.
 */
public interface RecipeRepository extends MongoRepository<Recipe, String> {

	List<Recipe> findByTitleContainingIgnoreCase(String q);

	List<Recipe> findByCategoriesIn(List<String> categories);

	List<Recipe> findByTagsIn(List<String> tags);
}

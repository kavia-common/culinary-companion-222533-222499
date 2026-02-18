package com.example.recipebackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipebackend.domain.RecipeRating;

/**
 * Mongo repository for ratings.
 */
public interface RecipeRatingRepository extends MongoRepository<RecipeRating, String> {
	Optional<RecipeRating> findByRecipeIdAndUserId(String recipeId, String userId);

	List<RecipeRating> findByRecipeId(String recipeId);
}

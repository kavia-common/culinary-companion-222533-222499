package com.example.recipebackend.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipebackend.domain.RecipeComment;

/**
 * Mongo repository for comments.
 */
public interface RecipeCommentRepository extends MongoRepository<RecipeComment, String> {
	List<RecipeComment> findByRecipeIdOrderByCreatedAtDesc(String recipeId);
}

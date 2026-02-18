package com.example.recipebackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.recipebackend.domain.RecipeComment;
import com.example.recipebackend.repo.RecipeCommentRepository;

/**
 * Service for recipe comments.
 */
@Service
public class CommentsService {

	private final RecipeCommentRepository repo;

	public CommentsService(RecipeCommentRepository repo) {
		this.repo = repo;
	}

	public RecipeComment add(String recipeId, String userId, String text) {
		RecipeComment c = new RecipeComment();
		c.setRecipeId(recipeId);
		c.setUserId(userId);
		c.setText(text);
		return repo.save(c);
	}

	public List<RecipeComment> list(String recipeId) {
		return repo.findByRecipeIdOrderByCreatedAtDesc(recipeId);
	}
}

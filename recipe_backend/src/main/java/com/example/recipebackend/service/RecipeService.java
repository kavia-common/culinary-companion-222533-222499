package com.example.recipebackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.recipebackend.api.NotFoundException;
import com.example.recipebackend.api.dto.UpsertRecipeRequest;
import com.example.recipebackend.domain.Recipe;
import com.example.recipebackend.repo.RecipeRepository;

/**
 * Service for recipe operations.
 */
@Service
public class RecipeService {

	private final RecipeRepository recipes;

	public RecipeService(RecipeRepository recipes) {
		this.recipes = recipes;
	}

	public Recipe create(String userId, UpsertRecipeRequest req) {
		Recipe r = new Recipe();
		r.setTitle(req.title());
		r.setDescription(req.description());
		r.setIngredients(req.ingredients());
		r.setSteps(req.steps());
		r.setCategories(Optional.ofNullable(req.categories()).orElseGet(ArrayList::new));
		r.setTags(Optional.ofNullable(req.tags()).orElseGet(ArrayList::new));
		r.setPhotoUrl(req.photoUrl());
		r.setAuthorUserId(userId);
		return recipes.save(r);
	}

	public Recipe update(String id, String userId, UpsertRecipeRequest req) {
		Recipe r = recipes.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found: " + id));
		// In a real app, authorize userId == authorUserId.
		r.setTitle(req.title());
		r.setDescription(req.description());
		r.setIngredients(req.ingredients());
		r.setSteps(req.steps());
		r.setCategories(Optional.ofNullable(req.categories()).orElseGet(ArrayList::new));
		r.setTags(Optional.ofNullable(req.tags()).orElseGet(ArrayList::new));
		r.setPhotoUrl(req.photoUrl());
		r.touchUpdated();
		return recipes.save(r);
	}

	public void delete(String id) {
		if (!recipes.existsById(id)) {
			throw new NotFoundException("Recipe not found: " + id);
		}
		recipes.deleteById(id);
	}

	public Recipe get(String id) {
		return recipes.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found: " + id));
	}

	public List<Recipe> list(String q, String category, String tag) {
		List<Recipe> result;
		if (q != null && !q.isBlank()) {
			result = recipes.findByTitleContainingIgnoreCase(q.trim());
		} else if (category != null && !category.isBlank()) {
			result = recipes.findByCategoriesIn(List.of(category.trim()));
		} else if (tag != null && !tag.isBlank()) {
			result = recipes.findByTagsIn(List.of(tag.trim()));
		} else {
			result = recipes.findAll();
		}

		result.sort(Comparator.comparing(Recipe::getCreatedAt).reversed());
		return result;
	}
}

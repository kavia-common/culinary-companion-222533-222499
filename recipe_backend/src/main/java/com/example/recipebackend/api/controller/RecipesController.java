package com.example.recipebackend.api.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.api.dto.UpsertRecipeRequest;
import com.example.recipebackend.domain.Recipe;
import com.example.recipebackend.service.RatingsService;
import com.example.recipebackend.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Recipes CRUD and search endpoints.
 */
@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes", description = "Browse, search, and manage recipes")
public class RecipesController {

	private final RecipeService recipes;
	private final RatingsService ratings;
	private final UserContext userContext;

	public RecipesController(RecipeService recipes, RatingsService ratings, UserContext userContext) {
		this.recipes = recipes;
		this.ratings = ratings;
		this.userContext = userContext;
	}

	// PUBLIC_INTERFACE
	@GetMapping
	@Operation(
		summary = "List recipes",
		description = "Lists recipes. Optional query params: q (title search), category, tag."
	)
	public List<Recipe> list(
		@RequestParam(value = "q", required = false) String q,
		@RequestParam(value = "category", required = false) String category,
		@RequestParam(value = "tag", required = false) String tag
	) {
		return recipes.list(q, category, tag);
	}

	// PUBLIC_INTERFACE
	@PostMapping
	@Operation(summary = "Create recipe", description = "Creates a new recipe. Requires X-User-Id header (lightweight auth).")
	public Recipe create(
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody UpsertRecipeRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return recipes.create(userId, request);
	}

	// PUBLIC_INTERFACE
	@GetMapping("/{id}")
	@Operation(summary = "Get recipe", description = "Fetch a recipe by id.")
	public Recipe get(@PathVariable("id") String id) {
		return recipes.get(id);
	}

	// PUBLIC_INTERFACE
	@PutMapping("/{id}")
	@Operation(summary = "Update recipe", description = "Updates a recipe. Requires X-User-Id header (lightweight auth).")
	public Recipe update(
		@PathVariable("id") String id,
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody UpsertRecipeRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return recipes.update(id, userId, request);
	}

	// PUBLIC_INTERFACE
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete recipe", description = "Deletes a recipe by id.")
	public Map<String, Object> delete(@PathVariable("id") String id) {
		recipes.delete(id);
		return Map.of("deleted", true, "id", id);
	}

	// PUBLIC_INTERFACE
	@GetMapping("/{id}/rating")
	@Operation(summary = "Get recipe rating summary", description = "Returns average rating and count for a recipe.")
	public Map<String, Object> ratingSummary(@PathVariable("id") String id) {
		return Map.of(
			"recipeId", id,
			"average", ratings.average(id),
			"count", ratings.count(id)
		);
	}
}

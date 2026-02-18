package com.example.recipebackend.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Request to rate a recipe.
 */
public record RateRecipeRequest(
	@Min(value = 1, message = "stars must be between 1 and 5")
	@Max(value = 5, message = "stars must be between 1 and 5")
	int stars
) {}

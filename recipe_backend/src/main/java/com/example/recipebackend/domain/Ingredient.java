package com.example.recipebackend.domain;

import jakarta.validation.constraints.NotBlank;

/**
 * Ingredient item for a recipe and for shopping lists.
 */
public record Ingredient(
	@NotBlank(message = "name is required")
	String name,
	String quantity,
	String unit
) {}

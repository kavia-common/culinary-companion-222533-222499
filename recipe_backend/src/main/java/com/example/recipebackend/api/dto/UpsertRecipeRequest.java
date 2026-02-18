package com.example.recipebackend.api.dto;

import java.util.List;

import com.example.recipebackend.domain.Ingredient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating/updating recipes.
 */
public record UpsertRecipeRequest(
	@NotBlank(message = "title is required")
	String title,
	String description,
	@NotNull(message = "ingredients is required")
	@Valid
	List<Ingredient> ingredients,
	@NotNull(message = "steps is required")
	List<@NotBlank(message = "step must not be blank") String> steps,
	List<String> categories,
	List<String> tags,
	String photoUrl
) {}

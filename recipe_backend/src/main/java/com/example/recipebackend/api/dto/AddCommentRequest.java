package com.example.recipebackend.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request to add a comment to a recipe.
 */
public record AddCommentRequest(
	@NotBlank(message = "text is required")
	String text
) {}

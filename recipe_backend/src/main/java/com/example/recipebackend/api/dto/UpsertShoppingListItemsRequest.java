package com.example.recipebackend.api.dto;

import java.util.List;

import com.example.recipebackend.domain.Ingredient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Replace shopping list items for a user.
 */
public record UpsertShoppingListItemsRequest(
	@NotNull(message = "items is required")
	@Valid
	List<Ingredient> items
) {}

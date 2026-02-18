package com.example.recipebackend.api.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.domain.UserFavorites;
import com.example.recipebackend.service.FavoritesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Favorites endpoints.
 */
@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "Manage user favorite recipes")
public class FavoritesController {

	private final FavoritesService favorites;
	private final UserContext userContext;

	public FavoritesController(FavoritesService favorites, UserContext userContext) {
		this.favorites = favorites;
		this.userContext = userContext;
	}

	// PUBLIC_INTERFACE
	@GetMapping
	@Operation(summary = "List favorites", description = "Lists favorite recipe IDs for the current user (X-User-Id).")
	public Set<String> list(@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader) {
		String userId = userContext.requireUserId(userIdHeader);
		return favorites.list(userId);
	}

	// PUBLIC_INTERFACE
	@PostMapping("/{recipeId}")
	@Operation(summary = "Add favorite", description = "Adds a recipe to favorites for the current user (X-User-Id).")
	public UserFavorites add(
		@PathVariable("recipeId") String recipeId,
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return favorites.add(userId, recipeId);
	}

	// PUBLIC_INTERFACE
	@DeleteMapping("/{recipeId}")
	@Operation(summary = "Remove favorite", description = "Removes a recipe from favorites for the current user (X-User-Id).")
	public UserFavorites remove(
		@PathVariable("recipeId") String recipeId,
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return favorites.remove(userId, recipeId);
	}

	// PUBLIC_INTERFACE
	@GetMapping("/me")
	@Operation(summary = "Get favorites doc", description = "Returns the favorites document for the user (debug convenience).")
	public Map<String, Object> getDoc(@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader) {
		String userId = userContext.requireUserId(userIdHeader);
		return Map.of("userId", userId, "recipeIds", favorites.list(userId));
	}
}

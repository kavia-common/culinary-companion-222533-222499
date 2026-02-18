package com.example.recipebackend.api.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.api.dto.RateRecipeRequest;
import com.example.recipebackend.domain.RecipeRating;
import com.example.recipebackend.service.RatingsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Ratings endpoints.
 */
@RestController
@RequestMapping("/api/recipes/{recipeId}/ratings")
@Tag(name = "Ratings", description = "Rate recipes and view rating stats")
public class RatingsController {

	private final RatingsService ratings;
	private final UserContext userContext;

	public RatingsController(RatingsService ratings, UserContext userContext) {
		this.ratings = ratings;
		this.userContext = userContext;
	}

	// PUBLIC_INTERFACE
	@PostMapping
	@Operation(summary = "Rate recipe", description = "Rates a recipe (1-5). Requires X-User-Id header.")
	public RecipeRating rate(
		@PathVariable("recipeId") String recipeId,
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody RateRecipeRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return ratings.rate(recipeId, userId, request.stars());
	}

	// PUBLIC_INTERFACE
	@GetMapping("/summary")
	@Operation(summary = "Rating summary", description = "Returns average and count rating summary.")
	public Map<String, Object> summary(@PathVariable("recipeId") String recipeId) {
		return Map.of(
			"recipeId", recipeId,
			"average", ratings.average(recipeId),
			"count", ratings.count(recipeId)
		);
	}
}

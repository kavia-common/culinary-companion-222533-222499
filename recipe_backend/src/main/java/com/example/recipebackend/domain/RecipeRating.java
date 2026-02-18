package com.example.recipebackend.domain;

import java.time.Instant;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Rating for a recipe. We enforce uniqueness (recipeId,userId) via a compound index.
 */
@Document(collection = "ratings")
@CompoundIndex(name = "rating_unique", def = "{'recipeId': 1, 'userId': 1}", unique = true)
public class RecipeRating {

	@Id
	private String id;

	@NotBlank(message = "recipeId is required")
	private String recipeId;

	@NotBlank(message = "userId is required")
	private String userId;

	@Min(value = 1, message = "stars must be between 1 and 5")
	@Max(value = 5, message = "stars must be between 1 and 5")
	private int stars;

	private Instant updatedAt = Instant.now();

	public RecipeRating() {}

	public void touch() {
		this.updatedAt = Instant.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}

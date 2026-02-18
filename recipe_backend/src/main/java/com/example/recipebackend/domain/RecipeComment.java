package com.example.recipebackend.domain;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User comment on a recipe.
 */
@Document(collection = "comments")
public class RecipeComment {

	@Id
	private String id;

	@NotBlank(message = "recipeId is required")
	private String recipeId;

	@NotBlank(message = "userId is required")
	private String userId;

	@NotBlank(message = "text is required")
	private String text;

	private Instant createdAt = Instant.now();

	public RecipeComment() {}

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}

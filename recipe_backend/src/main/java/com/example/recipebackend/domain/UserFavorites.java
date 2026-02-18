package com.example.recipebackend.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Stores favorite recipes per user.
 */
@Document(collection = "favorites")
public class UserFavorites {

	@Id
	private String id;

	private String userId;

	private Set<String> recipeIds = new HashSet<>();

	private Instant updatedAt = Instant.now();

	public UserFavorites() {}

	public UserFavorites(String userId) {
		this.userId = userId;
	}

	public void touch() {
		this.updatedAt = Instant.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<String> getRecipeIds() {
		return recipeIds;
	}

	public void setRecipeIds(Set<String> recipeIds) {
		this.recipeIds = recipeIds;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}

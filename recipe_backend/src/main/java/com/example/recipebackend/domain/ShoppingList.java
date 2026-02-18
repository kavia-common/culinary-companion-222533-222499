package com.example.recipebackend.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Shopping list per user.
 */
@Document(collection = "shopping_lists")
public class ShoppingList {

	@Id
	private String id;

	@NotBlank(message = "userId is required")
	private String userId;

	@Valid
	private List<Ingredient> items = new ArrayList<>();

	private Instant updatedAt = Instant.now();

	public ShoppingList() {}

	public ShoppingList(String userId) {
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

	public List<Ingredient> getItems() {
		return items;
	}

	public void setItems(List<Ingredient> items) {
		this.items = items;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}

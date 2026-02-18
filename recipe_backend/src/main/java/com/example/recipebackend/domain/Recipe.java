package com.example.recipebackend.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Recipe aggregate.
 */
@Document(collection = "recipes")
public class Recipe {

	@Id
	private String id;

	@NotBlank(message = "title is required")
	private String title;

	private String description;

	@Valid
	@NotNull(message = "ingredients is required")
	private List<Ingredient> ingredients = new ArrayList<>();

	@NotNull(message = "steps is required")
	private List<@NotBlank(message = "step must not be blank") String> steps = new ArrayList<>();

	private List<String> categories = new ArrayList<>();
	private List<String> tags = new ArrayList<>();

	/**
	 * Optional photo URL (e.g., from Cloudinary) - kept as string.
	 */
	private String photoUrl;

	/**
	 * For a real app this would come from auth; in this template we accept a userId header.
	 */
	private String authorUserId;

	private Instant createdAt = Instant.now();
	private Instant updatedAt = Instant.now();

	public Recipe() {
	}

	public void touchUpdated() {
		this.updatedAt = Instant.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getAuthorUserId() {
		return authorUserId;
	}

	public void setAuthorUserId(String authorUserId) {
		this.authorUserId = authorUserId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}

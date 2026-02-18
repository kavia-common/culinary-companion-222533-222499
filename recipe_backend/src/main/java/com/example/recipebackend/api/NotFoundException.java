package com.example.recipebackend.api;

/**
 * Thrown when a requested resource cannot be found.
 */
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}

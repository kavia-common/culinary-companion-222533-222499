package com.example.recipebackend.api;

import java.time.Instant;
import java.util.Map;

/**
 * Standard JSON error shape for API errors.
 */
public record ApiError(
	Instant timestamp,
	int status,
	String error,
	String message,
	String path,
	Map<String, Object> details
) {}

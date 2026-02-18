package com.example.recipebackend.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Lightweight health endpoint for the app (separate from actuator).
 */
@RestController
@Tag(name = "Health", description = "Basic service health endpoints")
public class HealthController {

	// PUBLIC_INTERFACE
	@GetMapping("/api/health")
	@Operation(summary = "Health check", description = "Returns a basic OK status and server time.")
	public Map<String, Object> health() {
		return Map.of("ok", true, "service", "recipe_backend");
	}
}

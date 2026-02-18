package com.example.recipebackend.api;

import org.springframework.stereotype.Component;

/**
 * Helper to extract the calling user from a request header.
 *
 * Note: This project uses a lightweight approach (no real auth) so the frontend can function.
 * A production implementation would use Spring Security + JWT.
 */
@Component
public class UserContext {

	public static final String USER_ID_HEADER = "X-User-Id";

	// PUBLIC_INTERFACE
	public String requireUserId(String headerValue) {
		/** Returns the user id from header or throws if missing. */
		if (headerValue == null || headerValue.trim().isEmpty()) {
			throw new IllegalArgumentException("Missing required header: " + USER_ID_HEADER);
		}
		return headerValue.trim();
	}
}

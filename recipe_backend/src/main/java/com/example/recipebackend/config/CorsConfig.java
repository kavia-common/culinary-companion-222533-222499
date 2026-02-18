package com.example.recipebackend.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS for the REST API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${ALLOWED_ORIGINS:}")
	private String allowedOrigins;

	@Value("${ALLOWED_METHODS:GET,POST,PUT,DELETE,PATCH,OPTIONS}")
	private String allowedMethods;

	@Value("${ALLOWED_HEADERS:Content-Type,Authorization}")
	private String allowedHeaders;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		final List<String> origins = parseCsv(allowedOrigins);
		final List<String> methods = parseCsv(allowedMethods);
		final List<String> headers = parseCsv(allowedHeaders);

		registry.addMapping("/api/**")
			.allowedOrigins(origins.isEmpty() ? new String[] { "*" } : origins.toArray(String[]::new))
			.allowedMethods(methods.toArray(String[]::new))
			.allowedHeaders(headers.toArray(String[]::new))
			.allowCredentials(false)
			.maxAge(3600);
	}

	private static List<String> parseCsv(String value) {
		if (value == null) {
			return List.of();
		}

		final String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			return List.of();
		}

		return Arrays.stream(trimmed.split(","))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.collect(Collectors.toList());
	}
}

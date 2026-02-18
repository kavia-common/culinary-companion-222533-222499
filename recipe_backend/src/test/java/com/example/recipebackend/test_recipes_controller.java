package com.example.recipebackend;

import java.time.Instant;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.api.controller.RecipesController;
import com.example.recipebackend.domain.Ingredient;
import com.example.recipebackend.domain.Recipe;
import com.example.recipebackend.service.RatingsService;
import com.example.recipebackend.service.RecipeService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web-layer tests for {@link RecipesController}.
 *
 * Notes:
 * - These are slice tests using {@link WebMvcTest}; dependencies are mocked.
 * - We verify request validation + error mapping, and header handling through {@link UserContext}.
 */
@WebMvcTest(controllers = RecipesController.class)
public class test_recipes_controller {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RecipeService recipeService;

	@MockBean
	private RatingsService ratingsService;

	@MockBean
	private UserContext userContext;

	@Test
	void list_delegatesToService_andReturnsArray() throws Exception {
		Recipe r = new Recipe();
		r.setId("r1");
		r.setTitle("Nachos");
		r.setIngredients(List.of(new Ingredient("Chips", "1", "bag")));
		r.setSteps(List.of("Open bag"));
		r.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));

		when(recipeService.list("na", null, null)).thenReturn(List.of(r));

		mockMvc.perform(get("/api/recipes").queryParam("q", "na"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", Matchers.hasSize(1)))
			.andExpect(jsonPath("$[0].id").value("r1"))
			.andExpect(jsonPath("$[0].title").value("Nachos"));

		verify(recipeService).list("na", null, null);
		verifyNoMoreInteractions(recipeService);
	}

	@Test
	void create_missingHeader_returns400_andDoesNotCallService() throws Exception {
		// Simulate UserContext enforcing header requirement.
		when(userContext.requireUserId(null))
			.thenThrow(new IllegalArgumentException("Missing required header: " + UserContext.USER_ID_HEADER));

		String body = """
			{
			  "title": "Tacos",
			  "description": "Yum",
			  "ingredients": [{"name":"Tortilla"}],
			  "steps": ["Eat"]
			}
			""";

		mockMvc.perform(post("/api/recipes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value(400))
			.andExpect(jsonPath("$.message", Matchers.containsString("Missing required header")));

		verify(userContext).requireUserId(null);
		verifyNoInteractions(recipeService);
	}

	@Test
	void create_invalidBody_returns400_fieldErrors() throws Exception {
		// Header exists, but body is invalid (blank title, missing steps)
		when(userContext.requireUserId("u1")).thenReturn("u1");

		String body = """
			{
			  "title": "   ",
			  "ingredients": [{"name":"Tortilla"}],
			  "steps": []
			}
			""";

		mockMvc.perform(post("/api/recipes")
				.header(UserContext.USER_ID_HEADER, "u1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("Validation failed"))
			.andExpect(jsonPath("$.details.fieldErrors.title", Matchers.containsString("title is required")));

		verify(userContext).requireUserId("u1");
		verifyNoInteractions(recipeService);
	}

	@Test
	void ratingSummary_usesRatingsService() throws Exception {
		when(ratingsService.average("r42")).thenReturn(4.25);
		when(ratingsService.count("r42")).thenReturn(8L);

		mockMvc.perform(get("/api/recipes/r42/rating"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.recipeId").value("r42"))
			.andExpect(jsonPath("$.average").value(4.25))
			.andExpect(jsonPath("$.count").value(8));

		verify(ratingsService).average("r42");
		verify(ratingsService).count("r42");
	}
}

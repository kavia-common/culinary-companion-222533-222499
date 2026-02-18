package com.example.recipebackend;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.example.recipebackend.api.NotFoundException;
import com.example.recipebackend.api.dto.UpsertRecipeRequest;
import com.example.recipebackend.domain.Ingredient;
import com.example.recipebackend.domain.Recipe;
import com.example.recipebackend.repo.RecipeRepository;
import com.example.recipebackend.service.RecipeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RecipeService}.
 */
public class test_recipe_service {

	@Test
	void list_whenQProvided_usesTitleSearch_andSortsByCreatedDesc() {
		RecipeRepository repo = mock(RecipeRepository.class);
		RecipeService service = new RecipeService(repo);

		Recipe older = new Recipe();
		older.setId("1");
		older.setTitle("Old");
		older.setIngredients(List.of(new Ingredient("A", "1", "x")));
		older.setSteps(List.of("s"));
		older.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));

		Recipe newer = new Recipe();
		newer.setId("2");
		newer.setTitle("New");
		newer.setIngredients(List.of(new Ingredient("B", "1", "x")));
		newer.setSteps(List.of("s"));
		newer.setCreatedAt(Instant.parse("2024-02-01T00:00:00Z"));

		when(repo.findByTitleContainingIgnoreCase("na")).thenReturn(List.of(older, newer));

		List<Recipe> result = service.list("na", null, null);

		verify(repo).findByTitleContainingIgnoreCase("na");
		assertEquals(List.of(newer, older), result, "Expected results sorted by createdAt desc");
	}

	@Test
	void create_mapsFields_andSetsAuthorId() {
		RecipeRepository repo = mock(RecipeRepository.class);
		RecipeService service = new RecipeService(repo);

		when(repo.save(any(Recipe.class))).thenAnswer(inv -> inv.getArgument(0));

		UpsertRecipeRequest req = new UpsertRecipeRequest(
			"Nachos",
			"Cheesy",
			List.of(new Ingredient("Chips", "1", "bag")),
			List.of("Bake"),
			null,
			null,
			"https://example.com/photo.jpg"
		);

		Recipe created = service.create("u1", req);

		assertEquals("Nachos", created.getTitle());
		assertEquals("Cheesy", created.getDescription());
		assertEquals("u1", created.getAuthorUserId());
		assertNotNull(created.getCategories());
		assertNotNull(created.getTags());

		ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);
		verify(repo).save(captor.capture());
		assertEquals("u1", captor.getValue().getAuthorUserId());
	}

	@Test
	void delete_whenMissing_throwsNotFound() {
		RecipeRepository repo = mock(RecipeRepository.class);
		RecipeService service = new RecipeService(repo);

		when(repo.existsById("missing")).thenReturn(false);

		NotFoundException ex = assertThrows(NotFoundException.class, () -> service.delete("missing"));
		assertTrue(ex.getMessage().contains("missing"));
		verify(repo).existsById("missing");
		verify(repo, never()).deleteById(anyString());
	}

	@Test
	void update_whenNotFound_throwsNotFound() {
		RecipeRepository repo = mock(RecipeRepository.class);
		RecipeService service = new RecipeService(repo);

		when(repo.findById("r1")).thenReturn(Optional.empty());

		UpsertRecipeRequest req = new UpsertRecipeRequest(
			"T",
			null,
			List.of(new Ingredient("I", "1", "x")),
			List.of("S"),
			null,
			null,
			null
		);

		assertThrows(NotFoundException.class, () -> service.update("r1", "u1", req));
		verify(repo).findById("r1");
	}
}

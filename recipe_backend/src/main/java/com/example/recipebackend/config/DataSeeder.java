package com.example.recipebackend.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.recipebackend.domain.Ingredient;
import com.example.recipebackend.domain.Recipe;
import com.example.recipebackend.repo.RecipeRepository;

/**
 * Seeds a small set of recipes for local/dev use.
 */
@Component
public class DataSeeder implements CommandLineRunner {

	private final RecipeRepository recipes;

	public DataSeeder(RecipeRepository recipes) {
		this.recipes = recipes;
	}

	@Override
	public void run(String... args) throws Exception {
		if (recipes.count() > 0) {
			return;
		}

		recipes.save(makeRecipe(
			"Neon Nachos",
			"Retro crunchy nachos with a bright, tangy cheese sauce.",
			List.of(
				new Ingredient("Tortilla chips", "300", "g"),
				new Ingredient("Cheddar cheese", "200", "g"),
				new Ingredient("Pickled jalapeños", "50", "g"),
				new Ingredient("Salsa", "1", "cup")
			),
			List.of(
				"Preheat oven to 200°C / 392°F.",
				"Spread chips on a tray and sprinkle cheese evenly.",
				"Bake 7-10 minutes until cheese melts.",
				"Top with jalapeños and serve with salsa."
			),
			List.of("Snacks"),
			List.of("retro", "quick"),
			"https://images.unsplash.com/photo-1585238342028-4a35a9a2b7af?auto=format&fit=crop&w=1200&q=60"
		));

		recipes.save(makeRecipe(
			"Arcade Ramen Bowl",
			"Comforting ramen with a soy-ginger kick.",
			List.of(
				new Ingredient("Ramen noodles", "2", "packs"),
				new Ingredient("Soy sauce", "2", "tbsp"),
				new Ingredient("Ginger", "1", "tsp"),
				new Ingredient("Egg", "2", "pcs"),
				new Ingredient("Green onion", "2", "stalks")
			),
			List.of(
				"Boil noodles according to package instructions.",
				"Whisk soy sauce and ginger into hot broth/water.",
				"Soft-boil eggs (6-7 minutes), peel, and halve.",
				"Combine noodles and broth, top with egg and green onion."
			),
			List.of("Dinner"),
			List.of("noodles", "comfort"),
			"https://images.unsplash.com/photo-1604908176997-125f25cc500f?auto=format&fit=crop&w=1200&q=60"
		));
	}

	private static Recipe makeRecipe(
		String title,
		String description,
		List<Ingredient> ingredients,
		List<String> steps,
		List<String> categories,
		List<String> tags,
		String photoUrl
	) {
		Recipe r = new Recipe();
		r.setTitle(title);
		r.setDescription(description);
		r.setIngredients(ingredients);
		r.setSteps(steps);
		r.setCategories(categories);
		r.setTags(tags);
		r.setPhotoUrl(photoUrl);
		r.setAuthorUserId("demo-user");
		return r;
	}
}

package com.example.recipebackend.service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.recipebackend.domain.RecipeRating;
import com.example.recipebackend.repo.RecipeRatingRepository;

/**
 * Service for recipe ratings.
 */
@Service
public class RatingsService {

	private final RecipeRatingRepository repo;

	public RatingsService(RecipeRatingRepository repo) {
		this.repo = repo;
	}

	public RecipeRating rate(String recipeId, String userId, int stars) {
		RecipeRating rating = repo.findByRecipeIdAndUserId(recipeId, userId).orElseGet(RecipeRating::new);
		rating.setRecipeId(recipeId);
		rating.setUserId(userId);
		rating.setStars(stars);
		rating.touch();
		return repo.save(rating);
	}

	public double average(String recipeId) {
		List<RecipeRating> ratings = repo.findByRecipeId(recipeId);
		if (ratings.isEmpty()) {
			return 0.0;
		}
		DoubleSummaryStatistics stats = ratings.stream().mapToDouble(RecipeRating::getStars).summaryStatistics();
		return stats.getAverage();
	}

	public long count(String recipeId) {
		return repo.findByRecipeId(recipeId).size();
	}
}

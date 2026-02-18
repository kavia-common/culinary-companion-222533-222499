package com.example.recipebackend.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.recipebackend.domain.UserFavorites;
import com.example.recipebackend.repo.UserFavoritesRepository;

/**
 * Service managing favorites per user.
 */
@Service
public class FavoritesService {

	private final UserFavoritesRepository repo;

	public FavoritesService(UserFavoritesRepository repo) {
		this.repo = repo;
	}

	public UserFavorites getOrCreate(String userId) {
		return repo.findByUserId(userId).orElseGet(() -> repo.save(new UserFavorites(userId)));
	}

	public Set<String> list(String userId) {
		return getOrCreate(userId).getRecipeIds();
	}

	public UserFavorites add(String userId, String recipeId) {
		UserFavorites fav = getOrCreate(userId);
		fav.getRecipeIds().add(recipeId);
		fav.touch();
		return repo.save(fav);
	}

	public UserFavorites remove(String userId, String recipeId) {
		UserFavorites fav = getOrCreate(userId);
		fav.getRecipeIds().remove(recipeId);
		fav.touch();
		return repo.save(fav);
	}
}

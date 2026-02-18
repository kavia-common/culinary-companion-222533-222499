package com.example.recipebackend.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipebackend.domain.UserFavorites;

/**
 * Mongo repository for user favorites.
 */
public interface UserFavoritesRepository extends MongoRepository<UserFavorites, String> {
	Optional<UserFavorites> findByUserId(String userId);
}

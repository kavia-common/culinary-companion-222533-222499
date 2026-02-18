package com.example.recipebackend.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipebackend.domain.ShoppingList;

/**
 * Mongo repository for shopping lists.
 */
public interface ShoppingListRepository extends MongoRepository<ShoppingList, String> {
	Optional<ShoppingList> findByUserId(String userId);
}

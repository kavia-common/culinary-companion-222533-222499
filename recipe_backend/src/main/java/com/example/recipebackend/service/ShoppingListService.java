package com.example.recipebackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.recipebackend.domain.Ingredient;
import com.example.recipebackend.domain.ShoppingList;
import com.example.recipebackend.repo.ShoppingListRepository;

/**
 * Service for shopping list operations.
 */
@Service
public class ShoppingListService {

	private final ShoppingListRepository repo;

	public ShoppingListService(ShoppingListRepository repo) {
		this.repo = repo;
	}

	public ShoppingList getOrCreate(String userId) {
		return repo.findByUserId(userId).orElseGet(() -> repo.save(new ShoppingList(userId)));
	}

	public ShoppingList replaceItems(String userId, List<Ingredient> items) {
		ShoppingList list = getOrCreate(userId);
		list.setItems(new ArrayList<>(items));
		list.touch();
		return repo.save(list);
	}

	public ShoppingList addItems(String userId, List<Ingredient> toAdd) {
		ShoppingList list = getOrCreate(userId);
		list.getItems().addAll(toAdd);
		list.touch();
		return repo.save(list);
	}

	public ShoppingList clear(String userId) {
		ShoppingList list = getOrCreate(userId);
		list.setItems(new ArrayList<>());
		list.touch();
		return repo.save(list);
	}
}

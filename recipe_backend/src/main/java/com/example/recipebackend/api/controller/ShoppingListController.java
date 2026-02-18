package com.example.recipebackend.api.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.api.dto.UpsertShoppingListItemsRequest;
import com.example.recipebackend.domain.ShoppingList;
import com.example.recipebackend.service.ShoppingListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Shopping list endpoints.
 */
@RestController
@RequestMapping("/api/shopping-list")
@Tag(name = "Shopping List", description = "Create and manage a shopping list from recipe ingredients")
public class ShoppingListController {

	private final ShoppingListService lists;
	private final UserContext userContext;

	public ShoppingListController(ShoppingListService lists, UserContext userContext) {
		this.lists = lists;
		this.userContext = userContext;
	}

	// PUBLIC_INTERFACE
	@GetMapping
	@Operation(summary = "Get shopping list", description = "Returns the current user's shopping list. Requires X-User-Id header.")
	public ShoppingList get(@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader) {
		String userId = userContext.requireUserId(userIdHeader);
		return lists.getOrCreate(userId);
	}

	// PUBLIC_INTERFACE
	@PutMapping
	@Operation(summary = "Replace items", description = "Replaces shopping list items. Requires X-User-Id header.")
	public ShoppingList replace(
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody UpsertShoppingListItemsRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return lists.replaceItems(userId, request.items());
	}

	// PUBLIC_INTERFACE
	@PostMapping("/add")
	@Operation(summary = "Add items", description = "Adds items to shopping list. Requires X-User-Id header.")
	public ShoppingList add(
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody UpsertShoppingListItemsRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return lists.addItems(userId, request.items());
	}

	// PUBLIC_INTERFACE
	@PostMapping("/clear")
	@Operation(summary = "Clear list", description = "Clears shopping list. Requires X-User-Id header.")
	public ShoppingList clear(@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader) {
		String userId = userContext.requireUserId(userIdHeader);
		return lists.clear(userId);
	}
}

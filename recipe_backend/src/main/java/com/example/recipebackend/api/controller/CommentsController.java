package com.example.recipebackend.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.recipebackend.api.UserContext;
import com.example.recipebackend.api.dto.AddCommentRequest;
import com.example.recipebackend.domain.RecipeComment;
import com.example.recipebackend.service.CommentsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Comments endpoints.
 */
@RestController
@RequestMapping("/api/recipes/{recipeId}/comments")
@Tag(name = "Comments", description = "Ratings and comments on recipes")
public class CommentsController {

	private final CommentsService comments;
	private final UserContext userContext;

	public CommentsController(CommentsService comments, UserContext userContext) {
		this.comments = comments;
		this.userContext = userContext;
	}

	// PUBLIC_INTERFACE
	@GetMapping
	@Operation(summary = "List comments", description = "List comments for a recipe.")
	public List<RecipeComment> list(@PathVariable("recipeId") String recipeId) {
		return comments.list(recipeId);
	}

	// PUBLIC_INTERFACE
	@PostMapping
	@Operation(summary = "Add comment", description = "Add a comment to a recipe. Requires X-User-Id header.")
	public RecipeComment add(
		@PathVariable("recipeId") String recipeId,
		@RequestHeader(value = UserContext.USER_ID_HEADER, required = false) String userIdHeader,
		@Valid @RequestBody AddCommentRequest request
	) {
		String userId = userContext.requireUserId(userIdHeader);
		return comments.add(recipeId, userId, request.text());
	}
}

package com.example.recipebackend;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.recipebackend.api.controller.HealthController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web-layer tests for {@link HealthController}.
 */
@WebMvcTest(controllers = HealthController.class)
public class test_health_controller {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void health_returnsOkShape() throws Exception {
		mockMvc.perform(get("/api/health"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith("application/json"))
			.andExpect(jsonPath("$.ok").value(true))
			.andExpect(jsonPath("$.service").value("recipe_backend"))
			.andExpect(jsonPath("$.*", Matchers.aMapWithSize(Matchers.greaterThanOrEqualTo(2))));
	}
}

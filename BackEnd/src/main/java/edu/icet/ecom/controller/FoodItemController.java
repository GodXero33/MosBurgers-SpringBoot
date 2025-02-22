package edu.icet.ecom.controller;

import edu.icet.ecom.dto.FoodItem;
import edu.icet.ecom.service.custom.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class FoodItemController {
	private final FoodItemService foodItemService;

	@GetMapping("/items")
	public ResponseEntity<?> getAll () {
		final List<FoodItem> foodItems = this.foodItemService.getAll();

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "All food items.", "items", foodItems));
	}
}

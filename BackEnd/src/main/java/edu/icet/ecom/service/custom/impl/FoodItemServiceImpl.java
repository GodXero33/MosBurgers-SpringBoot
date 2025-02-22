package edu.icet.ecom.service.custom.impl;

import edu.icet.ecom.dto.FoodItem;
import edu.icet.ecom.entity.FoodItemEntity;
import edu.icet.ecom.repository.custom.FoodItemRepository;
import edu.icet.ecom.service.custom.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {
	private final FoodItemRepository foodItemRepository;
	private final ModelMapper mapper;

	@Override
	public List<FoodItem> getAll() {
		final List<FoodItemEntity> foodItemEntities = this.foodItemRepository.getAll();
		final List<FoodItem> foodItems = new ArrayList<>();

		foodItemEntities.forEach(foodItemEntity -> foodItems.add(this.mapper.map(foodItemEntity, FoodItem.class)));

		return foodItems;
	}
}

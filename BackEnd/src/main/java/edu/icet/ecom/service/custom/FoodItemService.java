package edu.icet.ecom.service.custom;

import edu.icet.ecom.dto.FoodItem;
import edu.icet.ecom.service.SuperService;

import java.util.List;

public interface FoodItemService extends SuperService {
	List<FoodItem> getAll ();
}

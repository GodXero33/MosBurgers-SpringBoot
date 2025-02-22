package edu.icet.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FoodItemEntity {
	private Integer id;
	private String name;
	private Double price;
	private Double discount;
	private String category;
}

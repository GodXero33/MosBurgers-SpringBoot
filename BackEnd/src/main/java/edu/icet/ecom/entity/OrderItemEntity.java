package edu.icet.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItemEntity {
	private Integer foodItemId;
	private Integer orderId;
	private Double pricePerUnit;
	private Integer quantity;
	private Double totalPrice;
}

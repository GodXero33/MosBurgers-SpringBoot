package edu.icet.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItem {
	private Integer foodItemId;
	private Integer orderId;
	private Double pricePerUnit;
	private Integer quantity;
	private Double totalPrice;
}

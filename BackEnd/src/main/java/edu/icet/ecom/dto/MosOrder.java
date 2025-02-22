package edu.icet.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MosOrder {
	private Integer id;
	private Integer customerId;
	private Integer adminId;
	private String placeDate;
	private Double totalAmount;
	private Double finalAmount;
	private Double discount;
	private List<OrderItem> items;
}

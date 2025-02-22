package edu.icet.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MosOrderEntity {
	private Integer id;
	private Integer customerId;
	private Integer adminId;
	private String placeDate;
	private Double totalAmount;
	private Double finalAmount;
	private Double discount;
	private List<OrderItemEntity> items;
}

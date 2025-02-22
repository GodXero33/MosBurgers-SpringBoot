package edu.icet.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerEntity {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
}

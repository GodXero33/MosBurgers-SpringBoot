package edu.icet.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Admin {
	private Integer id;
	private String name;
	private String phone;
	private Double salary;
	private String email;
	private String password;
	private String position;
	private String dob;
	private String address;
}

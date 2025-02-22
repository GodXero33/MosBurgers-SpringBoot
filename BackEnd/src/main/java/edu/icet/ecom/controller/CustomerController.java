package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService customerService;

	@GetMapping("/customers")
	public ResponseEntity<?> get (@RequestParam(value = "id", required = false) Integer id) {
		if (id == null) {
			final List<Customer> customers = this.customerService.getAll();

			return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "All customer.", "customers", customers));
		}

		if (id <= 0) return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", false, "message", "Customer can't have 0 or negative id."));

		final Customer customer = this.customerService.get(id);

		if (customer == null)
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", false, "message", "No customer found with given id."));

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "Customer found.", "customer", customer));
	}

	@PostMapping("/customers")
	public ResponseEntity<?> add (@RequestBody Customer customer) {
		if (customer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ok", false, "message", "No customer provided."));

		final int id = this.customerService.addAndGetID(customer);

		customer.setId(id);

		if (id > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ok", true, "message", "Customer added.", "customer", customer));
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "message", "Failed to add customer."));
	}
}

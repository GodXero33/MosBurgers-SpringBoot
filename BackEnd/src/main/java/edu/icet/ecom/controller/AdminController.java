package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Admin;
import edu.icet.ecom.service.custom.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/admin")
	public ResponseEntity<?> get (@RequestParam("name")  String name, @RequestParam(value = "password", required = false) String password) {
		final Admin admin = this.adminService.getByUserName(name);

		if (admin == null)
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", false, "message", "No admin found with given username."));

		if (admin.getPassword().equals(password))
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "Success.", "admin", admin));

		admin.setPassword(null);

		return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "Incorrect Password.", "admin", admin));
	}
}

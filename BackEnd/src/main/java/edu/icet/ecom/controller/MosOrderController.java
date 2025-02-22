package edu.icet.ecom.controller;

import edu.icet.ecom.dto.MosOrder;
import edu.icet.ecom.service.custom.MosOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MosOrderController {
	private final MosOrderService mosOrderService;

	@PostMapping("/order")
	public ResponseEntity<?> add (@RequestBody MosOrder mosOrder) {
		int a = 0;
		if (this.mosOrderService.add(mosOrder))
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("ok", true, "message", "Order has placed."));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ok", false, "message", "Order failed to place."));
	}
}

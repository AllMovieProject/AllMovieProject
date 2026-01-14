package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {
	
	private final ProductService pService;
	
	@PostMapping("/insert")
	public ResponseEntity<String> product_insert(@RequestBody ProductFormDTO dto) {
		String res = pService.productInsert(dto);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}

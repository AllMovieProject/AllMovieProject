package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.ProductFormDTO;
import com.sist.web.service.ProductService;
import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductItemVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {
	
	private final ProductService pService;
	
	@GetMapping("/manager/category")
	public ResponseEntity<List<ProductCategoryVO>> manager_category() {
		List<ProductCategoryVO> list = pService.productCategoryList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/manager/items")
	public ResponseEntity<List<ProductItemVO>> manager_items(@RequestParam("category_id") int category_id, @RequestParam("is_base") boolean is_base) {
		List<ProductItemVO> list = pService.productItemList(category_id, is_base);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert")
	public ResponseEntity<String> manager_insert(@RequestBody ProductFormDTO dto) {
		String res = pService.productInsert(dto);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}

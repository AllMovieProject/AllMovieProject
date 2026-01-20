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

import com.sist.web.dto.ComboFormDTO;
import com.sist.web.dto.ProductFormDTO;
import com.sist.web.service.ProductService;
import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductItemVO;
import com.sist.web.vo.StoreProductVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {
	
	private final ProductService pService;
	
	@GetMapping("/manager/category")
	public ResponseEntity<List<ProductCategoryVO>> manager_category() {
		List<ProductCategoryVO> list = null;
		try {
			list = pService.productCategoryList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/manager/item-list")
	public ResponseEntity<List<ProductItemVO>> manager_items(@RequestParam("category_id") int category_id, @RequestParam("isBase") boolean isBase) {
		List<ProductItemVO> list = null;
		try {
			list = pService.productItemList(category_id, isBase);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/manager/product-list")
	public ResponseEntity<List<StoreProductVO>> manager_product_list(@RequestParam("category_id") int category_id) {
		List<StoreProductVO> list = null;
		try {
			list = pService.storeProductListData(category_id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert-item")
	public ResponseEntity<String> manager_insert_item(@RequestBody ProductFormDTO dto) {
		String res = "";
		try {
			res = pService.productInsert(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert-combo")
	public ResponseEntity<String> manager_insert_combo(@RequestBody ComboFormDTO dto) {
		String res = "";
		try {
//			res = pService.productInsert(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}

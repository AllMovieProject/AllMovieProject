package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.web.dto.ComboFormDTO;
import com.sist.web.dto.ProductFormDTO;
import com.sist.web.service.ProductService;
import com.sist.web.vo.ProductCategoryVO;
import com.sist.web.vo.ProductComboVO;
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
	
	@GetMapping("/manager/combo-products")
	public ResponseEntity<List<StoreProductVO>> manager_combo_products() {
		List<StoreProductVO> list = null;
		try {
			list = pService.storeComboProductList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert-item")
	public ResponseEntity<String> manager_insert_item(@RequestPart("data") ProductFormDTO dto,
	        @RequestPart(value = "image", required = false) MultipartFile productImageFile) {
		String res = "";
		try {
			res = pService.productInsert(dto, productImageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert-combo")
	public ResponseEntity<String> manager_insert_combo(@RequestPart("data") ComboFormDTO dto,
	        @RequestPart(value = "image", required = false) MultipartFile productImageFile) {
		String res = "";
		try {
			res = pService.productComboInsert(dto, productImageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/combo/{product_id}")
	public ResponseEntity<List<ProductComboVO>> product_combo_detail(@PathVariable("product_id") int product_id) {
	  List<ProductComboVO> list = null;
	  try {
	    list = pService.productComboDetail(product_id);
	  } catch (Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/upgrade-options/{base_item_id}")
	public ResponseEntity<List<ProductItemVO>> get_upgrade_options(@PathVariable("base_item_id") int base_item_id) {
	  List<ProductItemVO> list = null;
	  try {
	    list = pService.getUpgradeOptions(base_item_id);
	  } catch (Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<>(list, HttpStatus.OK);
	}

}

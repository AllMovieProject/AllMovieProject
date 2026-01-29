package com.sist.web.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.CartService;
import com.sist.web.vo.CartVO;
import com.sist.web.vo.CartItemVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartRestController {

	private final CartService cService;

	@GetMapping("/list")
	public ResponseEntity<List<CartVO>> cartList(HttpSession session) {
		List<CartVO> list = null;
		try {
			String userid = (String) session.getAttribute("userid");
			if (userid == null) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
			list = cService.getCartList(userid);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<String> cartAdd(@RequestBody Map<String, Object> requestData, HttpSession session) {
		String res = "no";
		try {
			String userid = (String) session.getAttribute("userid");
			if (userid == null) {
				return new ResponseEntity<>("login_required", HttpStatus.UNAUTHORIZED);
			}
			
			// Cart 정보
			CartVO cartVO = new CartVO();
			cartVO.setUserid(userid);
			cartVO.setStore_id((Integer) requestData.get("store_id"));
			cartVO.setProduct_id((Integer) requestData.get("product_id"));
			cartVO.setQuantity((Integer) requestData.get("quantity"));
			
			// Cart Items 정보
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> optionsData = (List<Map<String, Object>>) requestData.get("options");
			List<CartItemVO> items = new ArrayList<>();
			
			if (optionsData != null) {
				for (Map<String, Object> option : optionsData) {
					CartItemVO item = new CartItemVO();
					item.setItem_id((Integer) option.get("item_id"));
					item.setQuantity((Integer) option.get("quantity"));
					items.add(item);
				}
			}
			
			res = cService.insertCart(cartVO, items);
		} catch (Exception e) {
			e.printStackTrace();	
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/update/{cart_id}/{quantity}")
	public ResponseEntity<String> cartUpdate(
		@PathVariable("cart_id") int cart_id,
		@PathVariable("quantity") int quantity) {
		String res = "no";
		try {
			res = cService.updateCartQuantity(cart_id, quantity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{cart_id}")
	public ResponseEntity<String> cartDelete(@PathVariable("cart_id") int cart_id) {
		String res = "no";
		try {
			res = cService.deleteCart(cart_id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/delete-selected")
	public ResponseEntity<String> cartDeleteSelected(@RequestBody List<Integer> cartIds) {
		String res = "no";
		try {
			res = cService.deleteSelectedCart(cartIds);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/delete-all")
	public ResponseEntity<String> cartDeleteAll(HttpSession session) {
		String res = "no";
		try {
			String userid = (String) session.getAttribute("userid");
			if (userid == null) {
				return new ResponseEntity<>("login_required", HttpStatus.UNAUTHORIZED);
			}
			res = cService.deleteAllCart(userid);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
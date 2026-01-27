package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.mapper.CartMapper;
import com.sist.web.vo.CartVO;
import com.sist.web.vo.CartItemVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartMapper mapper;

	@Override
	public List<CartVO> getCartList(String userid) {
		return mapper.getCartList(userid);
	}

	@Override
	@Transactional
	public String insertCart(CartVO vo, List<CartItemVO> items) {
		String res = "no";
		try {
			// 1. 장바구니 추가
			mapper.insertCart(vo);
			int cartId = vo.getCart_id(); // SelectKey로 가져온 값
			
			// 2. 장바구니 아이템 추가
			if (items != null && !items.isEmpty()) {
				for (CartItemVO item : items) {
					item.setCart_id(cartId);
					mapper.insertCartItem(item);
				}
			}
			
			res = "yes";
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 트랜잭션 롤백
		}
		return res;
	}

	@Override
	public String updateCartQuantity(int cart_id, int quantity) {
		String res = "no";
		try {
			mapper.updateCartQuantity(cart_id, quantity);
			res = "yes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String deleteCart(int cart_id) {
		String res = "no";
		try {
			mapper.deleteCart(cart_id);
			res = "yes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String deleteAllCart(String userid) {
		String res = "no";
		try {
			mapper.deleteAllCart(userid);
			res = "yes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String deleteSelectedCart(List<Integer> cartIds) {
		String res = "no";
		try {
			mapper.deleteSelectedCart(cartIds);
			res = "yes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
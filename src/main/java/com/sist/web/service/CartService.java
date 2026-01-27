package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.CartVO;
import com.sist.web.vo.CartItemVO;

public interface CartService {
	
	public List<CartVO> getCartList(String userid);
	public String insertCart(CartVO vo, List<CartItemVO> items);
	public String updateCartQuantity(int cart_id, int quantity);
	public String deleteCart(int cart_id);
	public String deleteAllCart(String userid);
	public String deleteSelectedCart(List<Integer> cartIds);

}
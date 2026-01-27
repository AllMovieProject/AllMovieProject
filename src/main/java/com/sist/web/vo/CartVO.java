package com.sist.web.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CartVO {
	
	private int cart_id, store_id, product_id, quantity;
	private String userid;
	private Date cart_regdate;
  
	private StoreProductVO pvo = new StoreProductVO();
  
	private List<CartItemVO> items;
	
}
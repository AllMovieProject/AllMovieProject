package com.sist.web.vo;

import lombok.Data;

@Data
public class CartItemVO {
	
	private int cart_item_id, cart_id, item_id, quantity;
  
	private ProductItemVO ivo = new ProductItemVO();
	
}
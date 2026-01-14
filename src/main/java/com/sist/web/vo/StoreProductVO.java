package com.sist.web.vo;

import lombok.Data;

@Data
public class StoreProductVO {
	
	private int product_id, item_id, product_price, discount;
	private String product_name, product_image, description, is_combo;

}
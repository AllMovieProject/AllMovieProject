package com.sist.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreProductVO {
	
	private int product_id, item_id, product_price, discount;
	private String product_name, product_image, description, is_combo;

}
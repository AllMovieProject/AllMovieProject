package com.sist.web.vo;

import lombok.Data;

@Data
public class ProductComboVO {
	
	private int combo_id, product_id, item_id, upgrade_price, item_quantity;
	private String is_upgrade;

}

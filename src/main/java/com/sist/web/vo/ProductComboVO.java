package com.sist.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductComboVO {
	
	private int combo_id, product_id, item_id, upgrade_price, item_quantity;
	private String is_upgrade;

}

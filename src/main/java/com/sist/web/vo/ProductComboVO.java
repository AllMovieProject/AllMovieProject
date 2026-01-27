package com.sist.web.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductComboVO {
	
	private int combo_id, product_id, item_id, upgrade_price, item_quantity;
	private String is_upgrade;
	
	// JOIN으로 가져올 product_item 정보
	private String item_name, item_size;
	private int item_price;
	  
	// 업그레이드 옵션 리스트
	private List<ProductItemVO> upgradeOptions;

}

package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class StoreStockVO {
	
	private int stock_id, store_id, product_id, stock_quantity, category_id;
	private String dbday;
	private Date stock_regdate, stock_moddate;
	private StoreProductVO pvo = new StoreProductVO();

}

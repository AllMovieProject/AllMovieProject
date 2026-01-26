package com.sist.web.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StoreStockVO {
	
	private int stock_id, store_id, product_id, stock_quantity;
	private String dbday;
	private Date stock_regdate, stock_moddate;
	private StoreProductVO pvo = new StoreProductVO();

}

package com.sist.web.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreStockVO {
	
	private int stock_id, store_id, item_id, stock_quantity;
	private Date stock_regdate, stock_moddate;

}

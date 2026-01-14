package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductItemVO {
	
	private int item_id, item_price, base_item_id, add_price;
	private String item_name, item_size, dbday;
	private Date regdate, moddate;

}
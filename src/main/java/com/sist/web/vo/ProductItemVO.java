package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ProductItemVO {
	
	private int item_id, item_price, add_price;
	private Integer base_item_id;
	private String item_name, item_size, dbday;
	private Date regdate, moddate;

}
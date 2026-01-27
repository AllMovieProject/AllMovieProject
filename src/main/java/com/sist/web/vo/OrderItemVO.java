package com.sist.web.vo;

import lombok.Data;

@Data
public class OrderItemVO {
	
    private int order_item_id;
    private int order_id;
    private int product_id;
    private int quantity;
    private int price;
    
    // JOIN용
    private StoreProductVO pvo = new StoreProductVO();
    
}
package com.sist.web.vo;

import lombok.Data;

@Data
public class OrderItemDetailVO {
	
    private int order_item_detail_id;
    private int order_item_id;
    private int item_id;
    private int quantity;
    
    // JOIN용
    private ProductItemVO ivo = new ProductItemVO();
    
}
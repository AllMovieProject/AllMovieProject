package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {
	
    private int order_id;
    private String userid;
    private int store_id;
    private String merchant_uid; // 결제의 merchant_uid와 연결
    private int total_amount;
    private String order_status; // pending, paid, preparing, completed, cancelled
    private Date order_date;
    private Date regdate;
    
}
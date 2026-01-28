package com.sist.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaymentRequestDTO {
	
    private int amount;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_email;
    private List<Integer> cart_ids; // cart_id 목록만 전송
    
}
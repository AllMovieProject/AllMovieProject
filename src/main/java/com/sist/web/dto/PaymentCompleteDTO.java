package com.sist.web.dto;

import lombok.Data;

@Data
public class PaymentCompleteDTO {
	
    private String merchant_uid;
    private String imp_uid;
    private String pay_method; // 결제 수단 추가 (card, trans, vbank 등)
    
}
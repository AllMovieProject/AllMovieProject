package com.sist.web.dto;

import lombok.Data;

@Data
public class PaymentCancelDTO {
	
    private String merchant_uid;
    private String reason; // 취소 사유 (선택)
    
}
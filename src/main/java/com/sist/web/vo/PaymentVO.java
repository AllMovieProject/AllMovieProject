package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {
	
    private int payment_id;
    private String userid;
    private String merchant_uid; // 주문번호
    private String imp_uid; // 이니시스 거래번호
    private int amount; // 결제금액
    private String payment_method; // 결제수단
    private String payment_status; // 결제상태 (ready, paid, failed, cancelled)
    private String buyer_name;
    private String buyer_tel;
    private String buyer_email;
    private Date payment_date;
    private Date regdate;
    
}
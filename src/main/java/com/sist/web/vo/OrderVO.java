package com.sist.web.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderVO {
	
    private int order_id;
    private String userid;
    private int store_id;
    private String merchant_uid;
    private int total_amount;
    private String order_status;
    private Date order_date;
    private Date regdate;
    private String dbday; // 날짜 포맷용
    
    // JOIN용
    private List<OrderItemVO> items; // 주문 상품 목록
    
    // 상태 상수 정의
    public static final String STATUS_RECEIVED = "received";      // 접수
    public static final String STATUS_REJECTED = "rejected";      // 거절
    public static final String STATUS_PREPARING = "preparing";    // 준비중
    public static final String STATUS_READY = "ready";           // 준비완료
    public static final String STATUS_CANCELLED = "cancelled";    // 취소
    public static final String STATUS_COMPLETED = "completed";    // 픽업완료
    
}
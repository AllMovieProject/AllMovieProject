package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.OrderVO;

public interface OrderService {
	
    public String updateOrderStatus(int order_id, String order_status);
    public OrderVO getOrderByMerchantUid(String merchant_uid);
    public List<OrderVO> getUserOrders(String userid);
    
}
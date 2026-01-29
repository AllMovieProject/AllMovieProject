package com.sist.web.service;

import java.util.List;
import java.util.Map;

import com.sist.web.vo.OrderVO;

public interface OrderService {
	
    public String updateOrderStatus(int order_id, String order_status);
    public String cancelOrder(int order_id);
    public OrderVO getOrderByMerchantUid(String merchant_uid);
    public OrderVO getOrderById(int order_id);
    public List<OrderVO> getUserOrders(String userid);

    // 매니저용
    public List<OrderVO> getStoreOrders(int store_id, String order_status);
    public Map<String, Object> getTodayOrderStats(int store_id);
    public String updateOrderStatusByManager(int order_id, String order_status, int store_id);
    
}
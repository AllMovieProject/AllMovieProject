package com.sist.web.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sist.web.mapper.OrderMapper;
import com.sist.web.vo.OrderVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;

    @Override
    public String updateOrderStatus(int order_id, String order_status) {
        String res = "no";
        try {
            if (!isValidStatus(order_status)) {
                return "invalid_status";
            }
            
            mapper.updateOrderStatusById(order_id, order_status);
            res = "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public OrderVO getOrderByMerchantUid(String merchant_uid) {
        OrderVO order = mapper.getOrderByMerchantUid(merchant_uid);
        if (order != null) {
            // 주문 상품 조회
            order.setItems(mapper.getOrderItems(order.getOrder_id()));
            // 각 상품의 옵션 조회
            if (order.getItems() != null) {
                for (var item : order.getItems()) {
                    item.setDetails(mapper.getOrderItemDetails(item.getOrder_item_id()));
                }
            }
        }
        return order;
    }

    @Override
    public List<OrderVO> getUserOrders(String userid) {
        List<OrderVO> orders = mapper.getUserOrders(userid);
        // 각 주문의 상품 정보 조회
        for (OrderVO order : orders) {
            order.setItems(mapper.getOrderItems(order.getOrder_id()));
            // 각 상품의 옵션 조회
            if (order.getItems() != null) {
                for (var item : order.getItems()) {
                    item.setDetails(mapper.getOrderItemDetails(item.getOrder_item_id()));
                }
            }
        }
        return orders;
    }
    
    private boolean isValidStatus(String status) {
        return status.equals(OrderVO.STATUS_RECEIVED) ||
               status.equals(OrderVO.STATUS_REJECTED) ||
               status.equals(OrderVO.STATUS_PREPARING) ||
               status.equals(OrderVO.STATUS_READY) ||
               status.equals(OrderVO.STATUS_CANCELLED) ||
               status.equals(OrderVO.STATUS_COMPLETED);
    }
    
}
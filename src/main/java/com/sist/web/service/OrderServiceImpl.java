package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.mapper.OrderMapper;
import com.sist.web.vo.OrderVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final PaymentService paymentService;
    private final SimpMessagingTemplate messagingTemplate;

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
    @Transactional
    public String updateOrderStatusByManager(int order_id, String order_status, int store_id) {
        try {
            // 상태 유효성 검증
            if (!isValidStatus(order_status)) {
                return "invalid_status";
            }
            
            // 거절 시 환불 처리
            if (OrderVO.STATUS_REJECTED.equals(order_status)) {
                OrderVO order = getOrderById(order_id);
                if (order != null) {
                    // 결제 환불 처리
                    String refundResult = paymentService.refundPayment(order.getMerchant_uid(), "매장 사정으로 주문 거절");
                    if (!"yes".equals(refundResult)) {
                        return "refund_failed";
                    }
                }
            }
            
            mapper.updateOrderStatusById(order_id, order_status);
            
            // 매니저용 WebSocket 알림
            messagingTemplate.convertAndSend("/topic/orders/" + store_id, "update");
            
            // 사용자용 WebSocket 알림
            Map<String, Object> userNotification = new HashMap<>();
            userNotification.put("type", "status_update");
            userNotification.put("order_id", order_id);
            userNotification.put("status", order_status);
            messagingTemplate.convertAndSend("/topic/user/orders", userNotification);
            
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    @Override
    public List<OrderVO> getStoreOrders(int store_id, String order_status) {
    	return mapper.getStoreOrders(store_id, order_status);
        // 각 주문의 상품 정보 조회
        /* n+1 문제 발생
        for (OrderVO order : orders) {
            order.setItems(mapper.getOrderItems(order.getOrder_id()));
            // 각 상품의 옵션 조회
            if (order.getItems() != null) {
                for (var item : order.getItems()) {
                    item.setDetails(mapper.getOrderItemDetails(item.getOrder_item_id()));
                }
            }
        }
        return orders;*/
    }

    @Override
    public Map<String, Object> getTodayOrderStats(int store_id) {
        return mapper.getTodayOrderStats(store_id);
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
    public OrderVO getOrderById(int order_id) {
        OrderVO order = mapper.getOrderById(order_id);
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

    @Override
    @Transactional
    public String cancelOrder(int order_id) {
        try {
            // 1. 주문 정보 조회
            OrderVO order = getOrderById(order_id);
            
            if (order == null) {
                return "order_not_found";
            }

            // 2. 취소 가능한 상태인지 확인
            if (!OrderVO.STATUS_RECEIVED.equals(order.getOrder_status()) && 
                !OrderVO.STATUS_PREPARING.equals(order.getOrder_status())) {
                return "cannot_cancel";
            }

            // 3. 결제 환불 처리
            String refundResult = paymentService.refundPayment(order.getMerchant_uid(), "고객 요청");
            
            if (!"yes".equals(refundResult)) {
                return refundResult;
            }
            
            // WebSocket으로 주문 업데이트 알림 전송
            messagingTemplate.convertAndSend("/topic/orders/" + order.getStore_id(), "update");

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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
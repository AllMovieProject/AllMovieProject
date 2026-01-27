package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.mapper.OrderMapper;
import com.sist.web.mapper.PaymentMapper;
import com.sist.web.vo.CartVO;
import com.sist.web.vo.OrderItemDetailVO;
import com.sist.web.vo.OrderItemVO;
import com.sist.web.vo.OrderVO;
import com.sist.web.vo.PaymentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public String createPayment(PaymentVO paymentVO, List<CartVO> cartItems) {
        try {
            // 1. 결제 정보 생성
            paymentMapper.insertPayment(paymentVO);

            // 2. 주문 정보 생성
            OrderVO orderVO = new OrderVO();
            orderVO.setUserid(paymentVO.getUserid());
            orderVO.setStore_id(cartItems.get(0).getStore_id());
            orderVO.setMerchant_uid(paymentVO.getMerchant_uid());
            orderVO.setTotal_amount(paymentVO.getAmount());
            orderMapper.insertOrder(orderVO);

            int orderId = orderVO.getOrder_id();

            // 3. 주문 상품 및 상세 정보 생성
            for (CartVO cart : cartItems) {
                OrderItemVO orderItem = new OrderItemVO();
                orderItem.setOrder_id(orderId);
                orderItem.setProduct_id(cart.getProduct_id());
                orderItem.setQuantity(cart.getQuantity());
                orderItem.setPrice(cart.getPvo().getProduct_price() - cart.getPvo().getDiscount());
                orderMapper.insertOrderItem(orderItem);

                int orderItemId = orderItem.getOrder_item_id();

                // 옵션 정보 저장
                if (cart.getItems() != null && !cart.getItems().isEmpty()) {
                    for (var item : cart.getItems()) {
                        OrderItemDetailVO detail = new OrderItemDetailVO();
                        detail.setOrder_item_id(orderItemId);
                        detail.setItem_id(item.getItem_id());
                        detail.setQuantity(item.getQuantity());
                        orderMapper.insertOrderItemDetail(detail);
                    }
                }
            }

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public String completePayment(String merchant_uid, String imp_uid) {
        try {
            // 결제 완료 처리
            PaymentVO payment = new PaymentVO();
            payment.setMerchant_uid(merchant_uid);
            payment.setImp_uid(imp_uid);
            payment.setPayment_status("paid");
            paymentMapper.updatePayment(payment);

            // 주문 상태 업데이트
            orderMapper.updateOrderStatus(merchant_uid, "paid");

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public String cancelPayment(String merchant_uid) {
        try {
            // 결제 취소 처리
            PaymentVO payment = new PaymentVO();
            payment.setMerchant_uid(merchant_uid);
            payment.setPayment_status("cancelled");
            paymentMapper.updatePayment(payment);

            // 주문 상태 업데이트
            orderMapper.updateOrderStatus(merchant_uid, "cancelled");

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
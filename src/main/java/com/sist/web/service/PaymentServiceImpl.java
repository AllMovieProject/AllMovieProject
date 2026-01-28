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

            // 2. 주문 정보 생성 (상태: received - 접수)
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
    public String completePayment(String merchant_uid, String imp_uid, String payment_method) {
        try {
            // 결제 완료 처리
            PaymentVO payment = new PaymentVO();
            payment.setMerchant_uid(merchant_uid);
            payment.setImp_uid(imp_uid);
            payment.setPayment_method(payment_method != null ? payment_method : "card"); // 실제 결제 수단 저장
            payment.setPayment_status("paid");
            paymentMapper.updatePayment(payment);

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

            // 주문 상태 업데이트 (취소)
            orderMapper.updateOrderStatus(merchant_uid, OrderVO.STATUS_CANCELLED);

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public String refundPayment(String merchant_uid, String reason) {
        try {
            // 1. 결제 정보 조회
            PaymentVO payment = paymentMapper.getPaymentByMerchantUid(merchant_uid);
            
            if (payment == null) {
                return "payment_not_found";
            }
            System.err.println("payment:" + payment.getPayment_status());
            if (!"paid".equals(payment.getPayment_status())) {
                return "not_paid";
            }

            // 2. 이니시스 환불 처리 (실제로는 아임포트 API 호출)
            // TODO: 실제 환불 API 연동
            // 여기서는 DB 상태만 변경
            
            // 3. 결제 상태를 환불로 변경
            PaymentVO refundPayment = new PaymentVO();
            refundPayment.setMerchant_uid(merchant_uid);
            refundPayment.setPayment_status("refunded");
            paymentMapper.updatePayment(refundPayment);

            // 4. 주문 상태를 취소로 변경
            orderMapper.updateOrderStatus(merchant_uid, OrderVO.STATUS_CANCELLED);

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
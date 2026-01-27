package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.PaymentVO;
import com.sist.web.vo.CartVO;

public interface PaymentService {
	
    public String createPayment(PaymentVO paymentVO, List<CartVO> cartItems);
    public String completePayment(String merchant_uid, String imp_uid);
    public String cancelPayment(String merchant_uid);
    
}
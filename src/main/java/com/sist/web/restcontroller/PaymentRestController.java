package com.sist.web.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.PaymentService;
import com.sist.web.vo.CartVO;
import com.sist.web.vo.PaymentVO;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentRestController {

    private final PaymentService pService;

    @PostMapping("/prepare")
    public ResponseEntity<Map<String, Object>> payment_prepare(
            @RequestBody Map<String, Object> requestData,
            HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(Map.of("result", "login_required"), HttpStatus.UNAUTHORIZED);
            }

            // merchant_uid 생성
            String merchantUid = "ORDER_" + System.currentTimeMillis();

            // 결제 정보 생성
            PaymentVO paymentVO = new PaymentVO();
            paymentVO.setUserid(userid);
            paymentVO.setMerchant_uid(merchantUid);
            paymentVO.setAmount((Integer) requestData.get("amount"));
            paymentVO.setBuyer_name((String) requestData.get("buyer_name"));
            paymentVO.setBuyer_tel((String) requestData.get("buyer_tel"));
            paymentVO.setBuyer_email((String) requestData.get("buyer_email"));

            List<CartVO> cartItems = (List<CartVO>) requestData.get("cart_items");

            String result = pService.createPayment(paymentVO, cartItems);

            return new ResponseEntity<>(Map.of(
                "result", result,
                "merchant_uid", merchantUid
            ), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<Map<String, String>> payment_complete(@RequestBody Map<String, String> data) {
        try {
            String merchant_uid = data.get("merchant_uid");
            String imp_uid = data.get("imp_uid");

            String result = pService.completePayment(merchant_uid, imp_uid);

            return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, String>> payment_cancel(@RequestBody Map<String, String> data) {
        try {
            String merchant_uid = data.get("merchant_uid");

            String result = pService.cancelPayment(merchant_uid);

            return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
package com.sist.web.restcontroller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.PaymentCancelDTO;
import com.sist.web.dto.PaymentCompleteDTO;
import com.sist.web.dto.PaymentRequestDTO;
import com.sist.web.service.CartService;
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
    private final CartService cService;

    @PostMapping("/prepare")
    public ResponseEntity<Map<String, Object>> paymentPrepare(
            @RequestBody PaymentRequestDTO requestData,
            HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(Map.of("result", "login_required"), HttpStatus.UNAUTHORIZED);
            }

            String merchantUid = "ORDER_" + System.currentTimeMillis();

            PaymentVO paymentVO = new PaymentVO();
            paymentVO.setUserid(userid);
            paymentVO.setMerchant_uid(merchantUid);
            paymentVO.setAmount(requestData.getAmount());
            paymentVO.setBuyer_name(requestData.getBuyer_name());
            paymentVO.setBuyer_tel(requestData.getBuyer_tel());
            paymentVO.setBuyer_email(requestData.getBuyer_email());

            List<CartVO> allCartItems = cService.getCartList(userid);
            
            List<CartVO> cartItems = allCartItems.stream()
                .filter(cart -> requestData.getCart_ids().contains(cart.getCart_id()))
                .collect(Collectors.toList());

            if (cartItems.isEmpty()) {
                return new ResponseEntity<>(Map.of("result", "no_items"), HttpStatus.BAD_REQUEST);
            }

            String result = pService.createPayment(paymentVO, cartItems);

            // cart_ids를 세션에 저장
            session.setAttribute("payment_cart_ids_" + merchantUid, requestData.getCart_ids());

            return new ResponseEntity<>(Map.of(
                "result", result,
                "merchant_uid", merchantUid
            ), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<Map<String, String>> paymentComplete(
            @RequestBody PaymentCompleteDTO data,
            HttpSession session) {
        try {
            String result = pService.completePayment(
                data.getMerchant_uid(), 
                data.getImp_uid(),
                data.getPay_method()
            );

            if ("yes".equals(result)) {
                // 장바구니에서 결제한 상품 삭제
                @SuppressWarnings("unchecked")
                List<Integer> cartIds = (List<Integer>) session.getAttribute("payment_cart_ids_" + data.getMerchant_uid());
                
                if (cartIds != null && !cartIds.isEmpty()) {
                    cService.deleteSelectedCart(cartIds);
                    session.removeAttribute("payment_cart_ids_" + data.getMerchant_uid());
                }
            }

            return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, String>> paymentCancel(@RequestBody PaymentCancelDTO data) {
        try {
            String result = pService.cancelPayment(data.getMerchant_uid());

            return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error", "message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
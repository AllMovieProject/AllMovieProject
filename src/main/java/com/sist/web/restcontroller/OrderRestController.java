package com.sist.web.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.OrderService;
import com.sist.web.vo.OrderVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderRestController {

    private final OrderService oService;

    // 주문 상세 조회
    @GetMapping("/detail/{merchant_uid}")
    public ResponseEntity<OrderVO> orderDetail(@PathVariable("merchant_uid") String merchant_uid) {
        try {
            OrderVO order = oService.getOrderByMerchantUid(merchant_uid);
            if (order == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 사용자 주문 목록
    @GetMapping("/list")
    public ResponseEntity<List<OrderVO>> orderList(HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            
            List<OrderVO> orders = oService.getUserOrders(userid);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/cancel/{order_id}")
    public ResponseEntity<Map<String, String>> orderCancel(@PathVariable("order_id") int order_id) {
        try {
            String result = oService.cancelOrder(order_id);
            
            String message = "";
            switch (result) {
                case "yes":
                    message = "주문이 취소되고 환불되었습니다.";
                    break;
                case "order_not_found":
                    message = "주문을 찾을 수 없습니다.";
                    break;
                case "cannot_cancel":
                    message = "취소할 수 없는 주문 상태입니다.";
                    break;
                case "payment_not_found":
                    message = "결제 정보를 찾을 수 없습니다.";
                    break;
                case "not_paid":
                    message = "결제되지 않은 주문입니다.";
                    break;
                default:
                    message = "주문 취소에 실패했습니다.";
            }
            
            return new ResponseEntity<>(Map.of("result", result, "message", message), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error", "message", "서버 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
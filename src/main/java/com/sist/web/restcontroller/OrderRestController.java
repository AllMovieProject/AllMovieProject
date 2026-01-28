package com.sist.web.restcontroller;

import java.util.List;

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
    public ResponseEntity<String> cancelOrder(@PathVariable("order_id") int order_id) {
        try {
            String result = oService.updateOrderStatus(order_id, OrderVO.STATUS_CANCELLED);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
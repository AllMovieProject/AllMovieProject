package com.sist.web.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.OrderService;
import com.sist.web.service.StoreService;
import com.sist.web.vo.OrderVO;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/manager")
public class ManagerOrderRestController {

    private final OrderService oService;
    private final StoreService sService;

    // 매니저의 매장 조회
    @GetMapping("/store-id")
    public ResponseEntity<Integer> getStoreId(HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            
            int storeId = sService.getStoreId(userid);
            return new ResponseEntity<>(storeId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 매장 주문 목록 조회
    @GetMapping("/list/{store_id}")
    public ResponseEntity<List<OrderVO>> getStoreOrders(
            @PathVariable("store_id") int store_id,
            @RequestParam(value = "status", required = false) String status,
            HttpSession session) {
        try {
            // TODO: 매니저 권한 체크
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            
            List<OrderVO> orders = oService.getStoreOrders(store_id, status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 오늘의 주문 통계
    @GetMapping("/stats/{store_id}")
    public ResponseEntity<Map<String, Object>> getTodayStats(
            @PathVariable("store_id") int store_id,
            HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            
            Map<String, Object> stats = oService.getTodayOrderStats(store_id);
            System.out.println(stats.get("total_count"));
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 주문 상태 변경
    @PutMapping("/status/{order_id}/{status}")
    public ResponseEntity<Map<String, String>> updateOrderStatus(
            @PathVariable("order_id") int order_id,
            @PathVariable("status") String status,
            @RequestParam("store_id") int store_id,
            HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(Map.of("result", "unauthorized"), HttpStatus.UNAUTHORIZED);
            }
            
            String result = oService.updateOrderStatusByManager(order_id, status, store_id);
            
            String message = "";
            switch (result) {
                case "yes":
                    message = getStatusMessage(status);
                    break;
                case "invalid_status":
                    message = "유효하지 않은 상태입니다.";
                    break;
                case "refund_failed":
                    message = "환불 처리에 실패했습니다.";
                    break;
                default:
                    message = "상태 변경에 실패했습니다.";
            }
            
            return new ResponseEntity<>(Map.of("result", result, "message", message), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("result", "error", "message", "서버 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 주문 상세 조회
    @GetMapping("/detail/{order_id}")
    public ResponseEntity<OrderVO> getOrderDetail(
            @PathVariable("order_id") int order_id,
            HttpSession session) {
        try {
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            
            OrderVO order = oService.getOrderById(order_id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getStatusMessage(String status) {
        switch (status) {
            case "received":
                return "주문이 접수되었습니다.";
            case "rejected":
                return "주문이 거절되었습니다. (환불 처리됨)";
            case "preparing":
                return "주문 준비 중입니다.";
            case "ready":
                return "픽업 준비가 완료되었습니다.";
            case "completed":
                return "픽업이 완료되었습니다.";
            default:
                return "상태가 변경되었습니다.";
        }
    }
    
}
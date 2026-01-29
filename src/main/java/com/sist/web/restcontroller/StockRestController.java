package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.StockService;
import com.sist.web.vo.StoreStockVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockRestController {
	
	private final StockService sService;

	@GetMapping("/manager/list")
	public ResponseEntity<List<StoreStockVO>> manager_stock_list(HttpSession session) {
		List<StoreStockVO> list = null;
		try {
			String userid = (String) session.getAttribute("userid");
			list = sService.storeStockListData(userid);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/manager/insert")
	public ResponseEntity<String> manager_stock_insert(@RequestBody List<StoreStockVO> list, HttpSession session) {
		String res = "no";
		try {
			String userid = (String) session.getAttribute("userid");
			res = sService.stockInsert(list, userid);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}

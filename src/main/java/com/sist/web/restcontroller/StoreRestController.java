package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.StoreService;
import com.sist.web.vo.StoreStockVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreRestController {

	private final StoreService sService;

	// Store별 재고 조회 - store_id로 조회
	@GetMapping("/list/data")
	public ResponseEntity<List<StoreStockVO>> storeStockList(@RequestParam("sid") int store_id) {
		List<StoreStockVO> list = null;
		try {
			list = sService.storeListData(store_id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}

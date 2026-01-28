package com.sist.web.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.StoreService;
import com.sist.web.vo.StoreStockVO;
import com.sist.web.vo.StoreVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreRestController {

	private final StoreService sService;

	// Store별 재고 조회 - store_id로 조회
	@GetMapping("/list/data")
	public ResponseEntity<List<StoreStockVO>> store_stock_list(@RequestParam("store_id") int store_id) {
		List<StoreStockVO> list = null;
		try {
			list = sService.storeListData(store_id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{store_id}/product/{product_id}")
	public ResponseEntity<StoreStockVO> store_product_detail(
	    @PathVariable("store_id") int store_id,
	    @PathVariable("product_id") int product_id) {
	  StoreStockVO vo = null;
	  try {
	    vo = sService.storeStockDetail(store_id, product_id);
	  } catch (Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<>(vo, HttpStatus.OK);
	}
	
	@GetMapping("/theater/list")
    public ResponseEntity<List<StoreVO>> getStoreList() {
        try {
            List<StoreVO> stores = sService.getAllStores();
            return new ResponseEntity<>(stores, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info/{store_id}")
    public ResponseEntity<StoreVO> getStoreInfo(@PathVariable("store_id") int store_id) {
        try {
            StoreVO store = sService.getStoreById(store_id);
            return new ResponseEntity<>(store, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

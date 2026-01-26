package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.StockMapper;
import com.sist.web.vo.StoreStockVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

	private final StockMapper mapper;

	@Override
	public List<StoreStockVO> storeStockListData(String userid) {
		return mapper.storeStockListData(userid);
	}

	@Override
	public String stockInsert(List<StoreStockVO> list, String userid) {
		String res = "no";
		for (StoreStockVO vo : list) {
			System.out.println("product_id: " + vo.getProduct_id());
			vo.setProduct_id(vo.getProduct_id());
			vo.setStock_quantity(vo.getStock_quantity());
			mapper.stockInsert(vo, userid);
		}
		res = "yes";
		return res;
	}

}

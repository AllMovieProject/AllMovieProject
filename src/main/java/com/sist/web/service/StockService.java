package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.StoreStockVO;

public interface StockService {

	public List<StoreStockVO> storeStockListData(String userid);
	public String stockInsert(List<StoreStockVO> list, String userid);
	
}

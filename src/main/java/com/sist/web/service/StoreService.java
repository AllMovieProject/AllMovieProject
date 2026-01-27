package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.StoreStockVO;

public interface StoreService {
	
	public List<StoreStockVO> storeListData(int store_id);
	public StoreStockVO storeStockDetail(int store_id, int product_id);

}

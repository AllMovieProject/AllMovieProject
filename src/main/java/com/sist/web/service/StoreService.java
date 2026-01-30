package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.StoreStockVO;
import com.sist.web.vo.StoreVO;

public interface StoreService {
	
	public List<StoreStockVO> storeListData(int store_id);
	public StoreStockVO storeStockDetail(int store_id, int product_id);
	public int getStoreId(String userid);
	public List<StoreVO> getAllStores();
    public StoreVO getStoreById(int store_id);
    public StoreVO storeDistanceData(double userLat, double userLng, int store_id);
    public List<StoreVO> storeNearByDistance(double userLat, double userLng);

}

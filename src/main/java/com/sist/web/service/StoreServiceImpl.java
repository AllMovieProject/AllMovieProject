package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.StoreMapper;
import com.sist.web.vo.StoreStockVO;
import com.sist.web.vo.StoreVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreMapper mapper;

	@Override
	public List<StoreStockVO> storeListData(int store_id) {
		return mapper.storeListData(store_id);
	}
	
	@Override
	public StoreStockVO storeStockDetail(int store_id, int product_id) {
	  return mapper.storeStockDetail(store_id, product_id);
	}

	@Override
	public int getStoreId(String userid) {
		return mapper.getStoreId(userid);
	}

	@Override
    public List<StoreVO> getAllStores() {
        return mapper.getAllStores();
    }

    @Override
    public StoreVO getStoreById(int store_id) {
        return mapper.getStoreById(store_id);
    }

}

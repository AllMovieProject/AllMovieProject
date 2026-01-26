package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.StoreMapper;
import com.sist.web.vo.StoreStockVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreMapper mapper;

	@Override
	public List<StoreStockVO> storeListData(int store_id) {
		return mapper.storeListData(store_id);
	}

}

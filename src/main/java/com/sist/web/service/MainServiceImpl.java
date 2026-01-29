package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.dto.HomeInfoDTO;
import com.sist.web.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
	
	private final MainMapper mapper;

	@Override
	public HomeInfoDTO homeListData() {
		HomeInfoDTO dto = new HomeInfoDTO();
		dto.setCarouList(mapper.carouListData());
		dto.setTrendList(mapper.trendListData());
		dto.setPopList(mapper.popListData());
		dto.setRecentList(mapper.recentListData());
		dto.setTopList(mapper.topListData());
		return dto;
	}

}

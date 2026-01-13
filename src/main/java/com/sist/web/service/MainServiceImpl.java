package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MainMapper;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
	
	private final MainMapper mapper;

	@Override
	public Map<String, List<MovieVO>> homeListData() {
		Map<String, List<MovieVO>> map = new HashMap<>();
		map.put("carouList", mapper.carouListData());
		map.put("trendList", mapper.trendListData());
		map.put("popList", mapper.popListData());
		map.put("recentList", mapper.recentListData());
		map.put("topList", mapper.topListData());
		return map;
	}

}

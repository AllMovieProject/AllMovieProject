package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.SeatMapper;
import com.sist.web.vo.SeatVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
	private final SeatMapper mapper;

	@Override
	public Map<String, Object> seatListData(int id) {
		Map<String, Object> map = new HashMap<>();
		List<SeatVO> list = mapper.seatListData(id);
		
		map.put("seat_list", list);
		return map;
	}

}

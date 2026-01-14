package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.SeatMapper;
import com.sist.web.vo.ScheduleSeatVO;
import com.sist.web.vo.SeatVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
	private final SeatMapper mapper;

	@Override
	public Map<String, Object> seatListData(int id) {
		Map<String, Object> map = new HashMap<>();
		
		List<SeatVO> seat_row = mapper.seatRowListData(id);
		List<SeatVO> seat_col = mapper.seatColListData(id);
		List<ScheduleSeatVO> seat_id = mapper.seatIdListData(id);
		
		map.put("seat_row", seat_row);
		map.put("seat_col", seat_col);
		map.put("seat_id", seat_id);
		
		return map;
	}

}

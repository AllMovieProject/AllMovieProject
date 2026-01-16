package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.sist.web.mapper.SeatMapper;
import com.sist.web.vo.ScheduleSeatVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.SeatVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
	
	private final SeatMapper mapper;

	@Override
	public Map<String, Object> seatListData(int id) {
		Map<String, Object> map = new HashMap<>();
		
		List<SeatVO> row_list = mapper.seatRowListData(id);
		List<SeatVO> col_list = mapper.seatColListData(id);
		List<ScheduleSeatVO> seatId_list = mapper.seatIdListData(id);
		ScheduleVO booking_info = mapper.bookingDataInfo(id);
		
		map.put("row_list", row_list);
		map.put("col_list", col_list);
		map.put("seatId_list", seatId_list);
		map.put("booking_info", booking_info);
		
		return map;
	}

}

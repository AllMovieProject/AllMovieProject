package com.sist.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sist.web.dto.SeatBookingDTO;
import com.sist.web.mapper.SeatMapper;
import com.sist.web.vo.MoviePriceVO;
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
		
		map.put("row_list", row_list);
		map.put("col_list", col_list);
		map.put("seatId_list", seatId_list);
		
		return map;
	}

    @Override
    public Map<String, Object> bookingInfoData(int id) {
        Map<String, Object> map = new HashMap<>();
        
        ScheduleVO schedule_info = mapper.schduleInfoData(id);
        MoviePriceVO price_info = mapper.priceInfoData(id);
        
        map.put("schedule_info", schedule_info);
        map.put("price_info", price_info);
        
        return map;
    }

    @Override
    public void seatBooking(SeatBookingDTO dto) {
        int schedule_id = dto.getSchedule_id();
        List<Integer> list = dto.getSelected_seats();
        
        ScheduleSeatVO ssvo = new ScheduleSeatVO();
        ssvo.setSchedule_id(schedule_id);
        
        for(Integer seat_id : list) {
            ssvo.setSeat_id(seat_id);
            mapper.scheduleSeatFlagUp(ssvo);
        }
    }

    @Override
    public void seatBookingCancel(SeatBookingDTO dto) {
        int schedule_id = dto.getSchedule_id();
        List<Integer> list = dto.getSelected_seats();
        
        ScheduleSeatVO ssvo = new ScheduleSeatVO();
        ssvo.setSchedule_id(schedule_id);
        
        for(Integer seat_id : list) {
            ssvo.setSeat_id(seat_id);
            mapper.scheduleSeatFlagDown(ssvo);
        }
    }

}

package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.SeatVO;

@Mapper
@Repository
public interface SeatMapper {
	@Select("SELECT distinct se.seat_row, se.seat_col, se.seat_id FROM seat se "
		  + "JOIN schedule s "
		  + "ON s.screen_id = se.screen_id "
		  + "JOIN schedule_seat ss "
		  + "ON ss.schedule_id = s.schedule_id "
		  + "WHERE s.schedule_id = #{id} "
		  + "ORDER BY seat_row ASC, seat_col ASC")
	public List<SeatVO> seatListData(int id);
	
}

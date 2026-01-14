package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.ScheduleSeatVO;
import com.sist.web.vo.SeatVO;

@Mapper
public interface SeatMapper {

	@Select("SELECT DISTINCT se.seat_row "
		  + "FROM seat se "
		  + "JOIN schedule s "
		  + "ON s.screen_id = se.screen_id "
		  + "WHERE s.schedule_id = #{id} "
		  + "ORDER BY se.seat_row ASC")
	public List<SeatVO> seatRowListData(int id);
	
	
	@Select("SELECT DISTINCT se.seat_col "
		  + "FROM seat se "
		  + "JOIN schedule s "
		  + "ON s.screen_id = se.screen_id "
		  + "WHERE s.schedule_id = #{id} "
		  + "ORDER BY se.seat_col ASC")
	public List<SeatVO> seatColListData(int id);

	@Select("SELECT se.seat_row, se.seat_col, ss.seat_id, ss.reservation_flag "
		  + "FROM schedule_seat ss "
		  + "JOIN seat se "
		  + "ON ss.seat_id = se.seat_id "
		  + "WHERE ss.schedule_id = #{id} "
		  + "ORDER BY se.seat_row ASC, se.seat_col ASC")
	public List<ScheduleSeatVO> seatIdListData(int id);
	
	// 결제가 되면
	@Update("UPDATE schedule_seat SET reservation_flag = 1 "
		  + "WHERE seat_id = #{id}")
	public void scheduleIdFlagUpdate();
	
	// 아이디 얻어서 예매 테이블 추가와 취소
}

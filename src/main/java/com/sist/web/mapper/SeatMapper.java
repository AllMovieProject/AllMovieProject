package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.dto.SeatBookingDTO;
import com.sist.web.vo.BookingSeatVO;
import com.sist.web.vo.BookingVO;
import com.sist.web.vo.MoviePriceVO;
import com.sist.web.vo.ScheduleSeatVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.SeatVO;

@Mapper
@Repository
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
	
	public ScheduleVO schduleInfoData(int id);
	
	@Select("SELECT * FROM movie_price WHERE schedule_id = #{id}")
	public MoviePriceVO priceInfoData(int id);
	
	@Update("UPDATE schedule_seat SET reservation_flag = 1 "
		  + "WHERE schedule_id = #{schedule_id} AND seat_id = #{seat_id}")
	public void scheduleSeatFlagUp(ScheduleSeatVO vo);
	
	@Update("UPDATE schedule_seat SET reservation_flag = 0 "
	      + "WHERE schedule_id = #{schedule_id} AND seat_id = #{seat_id}")
	public void scheduleSeatFlagDown(ScheduleSeatVO vo);
	
	@Select("SELECT booking_id_seq.NEXTVAL FROM DUAL")
	public int bookingIdSequenceData();
	
	@Insert("INSERT INTO booking(booking_id, schedule_id, member_id) VALUES(#{booking_id}, #{schedule_id}, #{member_id})")
	public void bookingInsert(BookingVO vo);
	
	@Insert("INSERT INTO booking_seat(booking_seat_id, booking_id, seat_id) VALUES(bookingseat_id_seq.NEXTVAL, #{booking_id}, #{seat_id})")
	public void bookingSeatInsert(BookingSeatVO vo);
}

package com.sist.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.MovieVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.TheaterVO;

@Mapper
@Repository
public interface BookingMapper {
	
	@Select("SELECT DISTINCT theater_region, region_no FROM theater ORDER BY region_no ASC")
    public List<TheaterVO> theaterRegionListData();
	
	public List<ScheduleVO> dynamicDateListData(Map<String, Object> map);

	public List<MovieVO> dynamicMovieListData(Map<String, Object> map);

	public List<TheaterVO> dynamicTheaterListData(Map<String, Object> map);
	
	public List<ScheduleVO> dynamicScheduleListData(Map<String, Object> map);
	
	@Select("SELECT COUNT(*) FROM schedule_seat WHERE schedule_id = #{id}")
	public int scheduleSeatCount(int schedule_id);
	
	@Select("SELECT COUNT(*) FROM schedule_seat "
	      + "WHERE schedule_id = #{id} "
	      + "AND reservation_flag = 0")
	public int scheduleReservatedSeatCount(int scheuld_id);
}

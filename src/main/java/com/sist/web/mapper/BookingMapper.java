package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.MovieVO;
import com.sist.web.vo.TheaterVO;

@Mapper
@Repository
public interface BookingMapper {
	
	/* 
	 * 메가박스 스케줄
	 * 날짜(시간 포함)이 가장 우선 및 기본값 들어감 {
	 * 		1-1. 영화 클릭 => 상영 영화관 업데이트
	 * 		1-2. 영화관 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 
	 *	 	2-1. 영화관 클릭 => 상영 영화 업데이트 및 스케줄 보여줌
	 * 		2-2. 영화 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 		} + 스케줄 보여줄 때마다 좌석 정보도 업데이트 + 스케줄 내부에 시간 설정까지
	 */
    
    
	@Select("SELECT DISTINCT m.movie_id, m.title, m.rating FROM movie m, schedule s "
		  + "WHERE m.movie_id = s.movie_id "
	      + "AND s.schedule_date >= SYSDATE")
	public List<MovieVO> bookingAvailableMovieListData();
	
	@Select("SELECT DISTINCT theater_region, region_no FROM theater ORDER BY region_no ASC")
    public List<TheaterVO> theaterRegionListData();
	
	@Select("SELECT theater_id, theater_name FROM theater "
	      + "WHERE region_no = #{no} "
	      + "ORDER BY theater_name ASC")
	public List<TheaterVO> theaterListData(int no);
	
	/*
	 * 날짜 영화 영화관 종합으로 받아서 스케줄 보여주기
	 * 영화관은 무조건 들어가야 하므로 if문 하지 않아도 됨
	 */
}

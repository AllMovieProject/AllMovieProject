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
	 * 처음 영화랑 영화관 보여주는 거는 vue로 안해도되는거임!!!!!!!!!
	 * 일반 컨트롤러로 보여줘고 클릭하면 vue로 데이터 주고 받으면서 업데이트
	 * 
	 * 메가박스 스케줄
	 * 날짜(시간 포함)이 가장 우선 및 기본값 들어감 {
	 * 		1-1. 영화 클릭 => 상영 영화관 업데이트
	 * 		1-2. 영화관 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 
	 *	 	2-1. 영화관 클릭 => 상영 영화 업데이트 및 스케줄 보여줌
	 * 		2-2. 영화 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 		} + 스케줄 보여줄 때마다 좌석 정보도 업데이트 + 스케줄 내부에 시간 설정까지
	 */
	@Select("SELECT DISTINCT m.title, m.rating FROM movie m, schedule s "
		  + "WHERE m.movie_id = s.movie_id")
	public List<MovieVO> bookingAvailableMovieListData();
	
	/*
	 * @Select("SELECT DISTINCT theater_region FROM theater") public List<String>
	 * 아니면 그냥 html에 일일이 작성할까 몇개 안되는데 
	 * @Select("SELECT COUNT(*) FROM theater WHERE theater_region = #{region}")
	 */
}

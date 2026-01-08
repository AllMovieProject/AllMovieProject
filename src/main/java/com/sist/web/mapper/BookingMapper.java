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
	
	/* 
	 * 메가박스 스케줄
	 * 날짜(시간 포함)이 가장 우선 및 기본값 들어감 {
	 * 		1-1. 영화 클릭 => 상영 영화관 업데이트
	 * 		1-2. 영화관 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 
	 *	 	2-1. 영화관 클릭 => 상영 영화 업데이트 및 스케줄 보여줌
	 * 		2-2. 영화 클릭 => 클릭한 영화와 상영관의 스케줄
	 * 		} + 스케줄 보여줄 때마다 좌석 정보도 업데이트 + 스케줄 내부에 시간 설정까지
	 * 
	 * 	상영관 데이터 가져올 떄 count와 합쳐서 보내면 json 형식으로 시간 영화 같이 이런 정보 가져올 수 있음
	 * 	강사님 busan-mapper.xml에 동적 쿼리
	 * 
	 * 		영화와 상영관 둘 다 서로의 데이터를 null이 아닐 때 <if>로
	 * 		
	 *   		<if test="#{theater_name} != null">
				AND 로 theater_name의 theater_id에 연결된 스크린 아이디에 연결된 스케줄 아이디까지
				</if>
	 * 
	 * 
	 *   movie나 theater에서 값을 바로 가져오지 말고 무조건 schedule을 거쳐서 가져와서 스케줄의 모든 데이터를 갖기
	 *   => 그래서 영화만 클릭해도 해당 개봉 날짜나 그런 정보 포함
	 *   	available을 0과 1로 받아와서 선택 가능 여부 제공 + 극장 리전 옆에 숫자 반복문으로 합 구하면 숫자 보여주기 가능
	 *   	영화와 극장을 묶어서 생각?
	 *   	영화를 <if 사용해서 없을 경우에는 극장 전체지만 영화가 있으면 그거에 따라서
	 *   		+ 날짜까지 오늘 날짜로 store에 변경 (날짜 페이지 함수 클릭이나 다른 함수 클릭할 때마다 날짜 초기화)
	 *   	theatervo에 count 추가해서 (theater region과 theater_name 분리하면 편하긴 할 듯)
	 *   
	 *   1/7
	 *   
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
	
	public List<ScheduleVO> dynamicDateListData(Map<String, Object> map);

	public List<MovieVO> dynamicMovieListData(Map<String, Object> map);

	public List<TheaterVO> dynamicTheaterListData(Map<String, Object> map);
	/*
	 * 날짜 영화 영화관 종합으로 받아서 스케줄 보여주기
	 * 영화관은 무조건 들어가야 하므로 if문 하지 않아도 됨
	 */
}

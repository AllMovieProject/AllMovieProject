package com.sist.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookingMapper {
	/*
	 * 1. 스케줄에서 영화 가져오기
	 * 1. 극장 그냥 셀렉트
	 * 2. 영화 또는 극장 또는 둘다 선택시 스케줄
	 * 스케줄에는 좌석까지
	 */

}

package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.ScheduleVO;

@Mapper
@Repository
public interface MypageMapper {
	public List<ScheduleVO> bookingListData(String id);
}

package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.ScheduleVO;

public interface MypageService {
	public List<ScheduleVO> bookingListData(String id);
}

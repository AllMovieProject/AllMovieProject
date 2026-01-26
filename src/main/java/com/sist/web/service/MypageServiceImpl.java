package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MypageMapper;
import com.sist.web.vo.ScheduleVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
	private final MypageMapper mapper;

	@Override
	public List<ScheduleVO> bookingListData(String id) {
		return mapper.bookingListData(id);
	}
	
}

package com.sist.web.service;
import java.util.*;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.GroupVisitMapper;
import com.sist.web.vo.GroupVisitVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class GroupVisitServiceImpl implements GroupVisitService{
	private final GroupVisitMapper mapper;

	@Override
	public List<GroupVisitVO> GroupVisitListData(int start) {
		// TODO Auto-generated method stub
		return mapper.GroupVisitListData(start);
	}

	@Override
	public int GroupVisitTotalPage() {
		// TODO Auto-generated method stub
		return mapper.GroupVisitTotalPage();
	}
}

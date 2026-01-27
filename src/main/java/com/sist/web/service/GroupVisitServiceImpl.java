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

	@Override
	public GroupVisitVO groupVisitDetailData(int gno) {
		// TODO Auto-generated method stub
		mapper.groupVisitHitIncrement(gno);
		return mapper.groupVisitDetailData(gno);
	}

	@Override
	public void groupVisitInsert(GroupVisitVO vo) {
		// TODO Auto-generated method stub
		mapper.groupVisitInsert(vo);
	}

	@Override
	public List<GroupVisitVO> groupVisitCateData(String cateGroup) {
		// TODO Auto-generated method stub
		return mapper.groupVisitCateData(cateGroup);
	}

	@Override
	public void groupVisitUpdate(GroupVisitVO vo) {
		// TODO Auto-generated method stub
		mapper.groupVisitUpdate(vo);
	}

	@Override
	public void groupVisitDelete(int gno) {
		// TODO Auto-generated method stub
		mapper.groupVisitDelete(gno);
	}
}

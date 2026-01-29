package com.sist.web.service;

import java.util.List;

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
		return mapper.GroupVisitListData(start);
	}

	@Override
	public int GroupVisitTotalPage() {
		return mapper.GroupVisitTotalPage();
	}

	@Override
	public GroupVisitVO groupVisitDetailData(int gno) {
		mapper.groupVisitHitIncrement(gno);
		return mapper.groupVisitDetailData(gno);
	}

	@Override
	public void groupVisitInsert(GroupVisitVO vo) {
		mapper.groupVisitInsert(vo);
	}

	@Override
	public List<GroupVisitVO> groupVisitCateData(String cateGroup) {
		return mapper.groupVisitCateData(cateGroup);
	}

	@Override
	public void groupVisitUpdate(GroupVisitVO vo) {
		mapper.groupVisitUpdate(vo);
	}

	@Override
	public void groupVisitDelete(int gno) {
		mapper.groupVisitDelete(gno);
	}
	
}

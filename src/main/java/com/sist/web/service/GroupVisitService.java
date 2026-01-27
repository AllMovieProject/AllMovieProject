package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.GroupVisitVO;

public interface GroupVisitService {
	public List<GroupVisitVO> GroupVisitListData(int start);
	public int GroupVisitTotalPage();
	public GroupVisitVO groupVisitDetailData(int gno);
	public void groupVisitInsert(GroupVisitVO vo);
	public List<GroupVisitVO> groupVisitCateData(String cateGroup);
	public void groupVisitUpdate(GroupVisitVO vo);
	public void groupVisitDelete(int gno);
}

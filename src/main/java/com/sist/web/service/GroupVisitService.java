package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.GroupVisitVO;

public interface GroupVisitService {
	public List<GroupVisitVO> GroupVisitListData(int start);
	public int GroupVisitTotalPage();
}

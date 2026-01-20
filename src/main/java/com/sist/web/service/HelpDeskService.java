package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.HelpDeskVO;

public interface HelpDeskService {
	public List<HelpDeskVO> helpDeskListData(int start);
	public int helpDeskTotalPage();
}

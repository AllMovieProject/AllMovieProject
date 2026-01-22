package com.sist.web.service;

import java.util.HashMap;
import java.util.List;


import com.sist.web.vo.HelpDeskVO;

public interface HelpDeskService {
	public List<HelpDeskVO> helpDeskListData(int start);
	public int helpDeskTotalPage();	
	public HelpDeskVO helpDeskDetailData(int hno);
	public void helpDeskInsert(HelpDeskVO vo);
	public List<HelpDeskVO> helpDeskCateData(String cateGroup);
}

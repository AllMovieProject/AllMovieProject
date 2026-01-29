package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.sist.web.mapper.HelpdeskMapper;
import com.sist.web.vo.HelpDeskVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HelpDeskServiceImpl implements HelpDeskService{
	
	private final HelpdeskMapper mapper;

	@Override
	public List<HelpDeskVO> helpDeskListData(int start) {
		return mapper.helpDeskListData(start);
	}

	@Override
	public int helpDeskTotalPage() {
		return mapper.helpDeskTotalPage();
	}

	@Override
	public HelpDeskVO helpDeskDetailData(int hno) {
		mapper.helpDeskHitIncrement(hno);
		return mapper.helpDeskDetailData(hno);
	}

	@Override
	public void helpDeskInsert(HelpDeskVO vo) {
		mapper.helpDeskInsert(vo);
	}

	@Override
	public List<HelpDeskVO> helpDeskCateData(String cateGroup) {
		return mapper.helpDeskCateData(cateGroup);
	}

	@Override
	public HelpDeskVO helpdeskUpdateData(int hno) {
		return mapper.helpDeskDetailData(hno);
	}

	@Override
	public void helpdeskUpdate(HelpDeskVO vo) {
		mapper.helpdeskUpdate(vo);
	}

	@Override
	public void helpdeskDelete(int hno) {
		mapper.boardDelete(hno);
	}

}

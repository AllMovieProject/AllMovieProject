package com.sist.web.service;

import java.util.List;
import java.util.Map;

import com.sist.web.vo.BoardVO;

public interface BoardService {
	public List<BoardVO> boradListData(int start);
	public int boardTotalPage();
}

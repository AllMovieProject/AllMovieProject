package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.BoardMapper;
import com.sist.web.vo.BoardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final BoardMapper mapper;

	@Override
	public List<BoardVO> boardListData(int start) {
		// TODO Auto-generated method stub
		return mapper.boardListData(start);
	}

	@Override
	public int boardTotalPage() {
		// TODO Auto-generated method stub
		return mapper.boardTotalPage();
	}
	
	@Override
	public List<BoardVO> boardCateData(String cateGroup) {
		// TODO Auto-generated method stub
		return mapper.boardCateData(cateGroup);
	}

	@Override
	public void boardInsert(BoardVO vo) {
		// TODO Auto-generated method stub
		mapper.boardInsert(vo);
	}

	@Override
	public BoardVO boardDetailData(int bno) {
		// TODO Auto-generated method stub
		mapper.boardHitIncrement(bno);
		return mapper.boardDetailData(bno);
	}

	@Override
	public void boardUpdate(BoardVO vo) {
		// TODO Auto-generated method stub
		mapper.boardUpdate(vo);
	}

	@Override
	public BoardVO boardUpdateData(int no) {
		// TODO Auto-generated method stub
		return mapper.boardDetailData(no);
	}

	@Override
	public void boardDelete(int bno) {
		// TODO Auto-generated method stub
		mapper.boardDelete(bno);
	}

	
	
}

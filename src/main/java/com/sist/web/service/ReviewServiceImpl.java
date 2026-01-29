package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.ReviewMapper;
import com.sist.web.vo.MovieReviewVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewMapper mapper;

	@Override
	public List<MovieReviewVO> reviewListData(int movie_id) {
		return mapper.reviewListData(movie_id);
	}

	@Override
	public void reviewInsert(MovieReviewVO vo) {
		vo.setMrating((int) (Math.random() * 6));
		mapper.reviewInsert(vo);
	}

}

package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.MovieReviewVO;

public interface ReviewService {
	
	public List<MovieReviewVO> reviewListData(int movie_id);
	public void reviewInsert(MovieReviewVO vo);

}

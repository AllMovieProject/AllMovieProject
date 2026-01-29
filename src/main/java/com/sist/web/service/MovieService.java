package com.sist.web.service;

import com.sist.web.dto.ListDTO;
import com.sist.web.vo.MovieVO;

public interface MovieService {
	
	public ListDTO<MovieVO> movieListData(int page);
	public MovieVO movieDetailData(int movieId);

}

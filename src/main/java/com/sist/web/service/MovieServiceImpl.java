package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MovieMapper;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
	
	private final MovieMapper mapper;

	@Override
	public MovieVO movieDetailData(int movieId) {
		return mapper.movieDetailData(movieId);
	}

}

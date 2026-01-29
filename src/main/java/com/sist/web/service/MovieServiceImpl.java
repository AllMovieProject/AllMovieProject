package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.commons.CommonPagination;
import com.sist.web.dto.ListDTO;
import com.sist.web.mapper.MovieMapper;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
	
	private final MovieMapper mapper;

	@Override
	public ListDTO<MovieVO> movieListData(int page) {
		if (page < 1) page = 1;
		List<MovieVO> list = mapper.movieListData(CommonPagination.getOffSet(page));
		int totalpage = mapper.movieTotalPage();
		ListDTO<MovieVO> dto = new ListDTO<>(list, page, totalpage);
		CommonPagination.setPagination(dto);
		return dto;
	}
	
	@Override
	public MovieVO movieDetailData(int movieId) {
		return mapper.movieDetailData(movieId);
	}
}

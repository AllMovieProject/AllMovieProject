package com.sist.web.dto;

import java.util.List;

import com.sist.web.vo.MovieVO;

import lombok.Data;

@Data
public class HomeInfoDTO {
	
	List<MovieVO> carouList, trendList, popList, recentList, topList;

}

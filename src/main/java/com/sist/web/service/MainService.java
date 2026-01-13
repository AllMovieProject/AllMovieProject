package com.sist.web.service;

import java.util.List;
import java.util.Map;

import com.sist.web.vo.MovieVO;

public interface MainService {
	
	public Map<String, List<MovieVO>> homeListData();

}

package com.sist.web.service;

import java.util.List;
import java.util.Map;

import com.sist.web.vo.MovieVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.TheaterVO;

public interface BookingService {

    public List<TheaterVO> theaterRegionListData();

	public List<ScheduleVO> dynamicDateListData(Map<String, Object> map);
    
    public List<MovieVO> dynamicMovieListData(Map<String, Object> map);
    
    public List<TheaterVO> dynamicTheaterListData(Map<String, Object> map);
}

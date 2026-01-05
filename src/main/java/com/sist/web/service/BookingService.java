package com.sist.web.service;

import java.util.List;

import com.sist.web.vo.MovieVO;
import com.sist.web.vo.TheaterVO;

public interface BookingService {
    
    public List<MovieVO> bookingAvailableMovieListData();
    
    public List<TheaterVO> theaterRegionListData();
    
    public List<TheaterVO> theaterListData(int no);
}

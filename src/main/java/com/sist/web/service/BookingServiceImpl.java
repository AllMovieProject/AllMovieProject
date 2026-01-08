package com.sist.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.BookingMapper;
import com.sist.web.vo.MovieVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.TheaterVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper mapper;

    @Override
    public List<TheaterVO> theaterRegionListData() {
        // TODO Auto-generated method stub
        return mapper.theaterRegionListData();
    }

	@Override
	public List<ScheduleVO> dynamicDateListData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.dynamicDateListData(map);
	}

    @Override
    public List<MovieVO> dynamicMovieListData(Map<String, Object> map) {
        return mapper.dynamicMovieListData(map);
    }

    @Override
    public List<TheaterVO> dynamicTheaterListData(Map<String, Object> map) {
        return mapper.dynamicTheaterListData(map);
    }
    
}

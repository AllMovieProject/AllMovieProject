package com.sist.web.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import com.sist.web.dto.BookingRequestDTO;
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
    public Map<String, Object> bookingListData(BookingRequestDTO dto) {
        Map<String, Object> map = new HashMap<>();

        Integer page = dto.getPage();
        String date = dto.getDate();
        Integer movie = dto.getMovie();
        Integer region = dto.getRegion();
        String theater = dto.getTheater();
        
        map.putAll(getDateList(movie, theater, page));
        map.putAll(getMovieList(date, region, theater));
        map.putAll(getTheaterList(date, movie, region));

        if (theater != null && !theater.equals("")) {
        	map.putAll(getScheduleData(date, movie, theater));
        }
        
        return map;
    }
    
    private Map<String, Object> getDateList(Integer movie, String theater, Integer page) {
        Map<String, Object> map = new HashMap<>();

        map.put("movie", movie);
        map.put("theater", theater);
        List<ScheduleVO> date_list = mapper.dynamicDateListData(map);

        date_list = dateListPagination(date_list, page);

        map = new HashMap<>();
        map.put("date_list", date_list);
        
        return map;
    }
    
    private Map<String, Object> getMovieList(String date, Integer region, String theater) {
        Map<String, Object> map = new HashMap<>();

        map.put("date", date);
        map.put("region", region);
        map.put("theater", theater);
        List<MovieVO> movie_list = mapper.dynamicMovieListData(map);

        map = new HashMap<>();
        map.put("movie_list", movie_list);

        return map;
    }

    private Map<String, Object> getTheaterList(String date, Integer movie, Integer region) {
        Map<String, Object> map = new HashMap<>();

        List<TheaterVO> region_list = mapper.theaterRegionListData();

        map.put("date", date);
        map.put("movie", movie);
        map.put("region", region);
        List<TheaterVO> theater_list = mapper.dynamicTheaterListData(map);

        Map<Integer, Integer> countMap = new HashMap<>();

        for (TheaterVO tvo : theater_list) {
            countMap.put(tvo.getRegion_no(), countMap.getOrDefault(tvo.getRegion_no(), 0) + 1);
        }

        for (TheaterVO vo : region_list) {
            vo.setCount(countMap.getOrDefault(vo.getRegion_no(), 0));
        }

        map = new HashMap<>();
        map.put("region_list", region_list);
        map.put("theater_list", theater_list);

        return map;
    }

    private Map<String, Object> getScheduleData(String date, Integer movie, String theater) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("movie", movie);
        map.put("theater", theater);
        List<ScheduleVO> schedule_list = mapper.dynamicScheduleListData(map);
        
        for (ScheduleVO vo : schedule_list) {
        	vo.setAvailable_count(mapper.scheduleReservatedSeatCount(vo.getSchedule_id()));
        	vo.setTotal_count(mapper.scheduleSeatCount(vo.getSchedule_id()));
        }
        
        map = new HashMap<>();
        map.put("schedule_list", schedule_list);

        return map;
    }
    
    private List<ScheduleVO> dateListPagination(List<ScheduleVO> date_list, int page) {
    	String firstDate = date_list.get(0).getSday();
        String lastDate = date_list.get(date_list.size() - 1).getSday();
        final int LIST_LEN = 10;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localStartDate = LocalDate.parse(firstDate, dateTimeFormatter);
        LocalDate localLastDate = LocalDate.parse(lastDate, dateTimeFormatter);

        long differenceInDays = ChronoUnit.DAYS.between(localStartDate, localLastDate);
        if (differenceInDays < LIST_LEN) {
            differenceInDays = LIST_LEN;
        }
        
        StringTokenizer st = new StringTokenizer(firstDate, "-");
        int year = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int day = Integer.parseInt(st.nextToken());
        
        YearMonth ym = YearMonth.of(year, month);
        int lastDay = ym.lengthOfMonth();

        List<ScheduleVO> list = new ArrayList<>();
        ScheduleVO vo = new ScheduleVO();
        String date = "";
        int count = 0;
        
        Calendar calendar = Calendar.getInstance();
        String[] weekDays = {"", "일", "월", "화", "수", "목", "금", "토"};
        String dayOfWeekStr = "";
        
		for(int i = 0; i < differenceInDays; i++) {
			date = String.format("%04d-%02d-%02d", year, month, day);
			vo.setAvailable_flag(0);
			
			if(count < date_list.size() && date_list.get(count).getSday().equals(date)) {
			    vo.setAvailable_flag(1);
			    count++;
			}
			
			calendar.set(year, month - 1, day);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            dayOfWeekStr = weekDays[dayOfWeek];

            vo.setSday(day + " / " + dayOfWeekStr);
            if (day == 1) {
                vo.setSday(month + "월 " + day + " / " + dayOfWeekStr);
            }
            
			list.add(vo);
			vo = new ScheduleVO();
			day++;
			
			if (day > lastDay) {
				month++;
				day = 1;
				
				if(month == 12) {
				    year++;
				    month = 1;
				}
				
				ym = YearMonth.of(year, month);
				lastDay = ym.lengthOfMonth();
			}
		}
		
		int startIndex = (LIST_LEN - 1) + (page - 1) * LIST_LEN;
		int endIndex = (LIST_LEN - 1) + (page - 1) * LIST_LEN;
		
		if (endIndex >= list.size()) {
		    endIndex = list.size() - 1;
		    startIndex = endIndex - (LIST_LEN - 1);
		}
		
		return list.subList(startIndex, endIndex);
    }

}

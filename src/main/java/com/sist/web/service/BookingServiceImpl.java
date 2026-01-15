package com.sist.web.service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
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
    final int date_len = 10;

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
        if (page == null) {
            page = 1;
        }
        Map<String, Object> map = new HashMap<>();

        map.put("movie", movie);
        map.put("theater", theater);
        List<ScheduleVO> date_list = mapper.dynamicDateListData(map);

        date_list = beautifulDateList(date_list, page);

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
    
    private Map<String, Object> getDateList1(Integer year, Integer month, Integer page) {

        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String today = df.format(date);
        StringTokenizer st = new StringTokenizer(today, "-");

        int yearNow = Integer.parseInt(st.nextToken());
        if (year == null) {
            year = yearNow;
        }

        int monthNow = Integer.parseInt(st.nextToken());
        if (month == null) {
            month = monthNow;
        }

        int dayNow = Integer.parseInt(st.nextToken());

        YearMonth ym = YearMonth.of(year, month);
        int lastDay = ym.lengthOfMonth();

        // 페이지네이션
        final int PAGE_SIZE = 10;
        int startDay = dayNow + (page - 1) * PAGE_SIZE;
        int endDay = dayNow + page * PAGE_SIZE;

        if (endDay > lastDay) {
            endDay = lastDay;
        }

        int count = 0;
        String booking_date = String.format("%04d-%02d-%02d", yearNow, monthNow, dayNow);

        for (int i = startDay; i <= endDay; i++) {
            String bDate = String.format("%04d-%02d-%02d", year, month, i);
            list.add(bDate);
            count++;
        }

        if (count < PAGE_SIZE && month == 12) {
            month = 1;
            year++;
        } else if (count < PAGE_SIZE) {
            month++;
        }

        ym = YearMonth.of(year, month);
        lastDay = ym.lengthOfMonth();

        for (int i = 1; i <= PAGE_SIZE - count; i++) {
            String bDate = String.format("%04d-%02d-%02d", year, month, i);
            list.add(bDate);
        }

        map.put("list", list);
        map.put("year", year);
        map.put("month", month);
        map.put("day", dayNow);
        map.put("booking_date", booking_date);
        map.put("page", page);

        return map;
    }
    
    private List<ScheduleVO> beautifulDateList(List<ScheduleVO> date_list, int page) {
    	String firstDay = date_list.get(0).getSday();
        StringTokenizer st = new StringTokenizer(firstDay, "-");
        int fYear = Integer.parseInt(st.nextToken());
        int fMonth = Integer.parseInt(st.nextToken());
        int fDay = Integer.parseInt(st.nextToken());
        
        String lastDay = date_list.get(date_list.size() - 1).getSday();
        st = new StringTokenizer(lastDay, "-");
        int lYear = Integer.parseInt(st.nextToken());
        int lMonth = Integer.parseInt(st.nextToken());
        int lDay = Integer.parseInt(st.nextToken());
        
		/*
		 * while (true) { if (countDay == lastDay && date_list.size() >= date_len) {
		 * break; }
		 * 
		 * break; }
		 */
        
		/*
		 * for (;date_list.size() == date_len;) {
		 *  fday를 1씩 증가하면서 lday와 같아질 때까지
		 * }
		 */

        YearMonth ym = YearMonth.of(fYear, fMonth);
        int firstLastDay = ym.lengthOfMonth();
        
        if (fYear < lYear) {
        	
        }
        
        int start = 0 + (page - 1) * 10;
        int end = date_len + (page - 1) * 10;
        if (end > date_list.size()) {
        	end =  date_list.size();
        }
        
        if (date_list.size() >= date_len) {
            date_list = date_list.subList(start, end);
        } else {
        }
        
        return date_list;
    }

    private List<ScheduleVO> addDateData(List<ScheduleVO> date_list) {
        StringTokenizer st = new StringTokenizer(date_list.get(date_list.size() - 1).getSday(), "-");
        int year = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int day = Integer.parseInt(st.nextToken());

        YearMonth ym = YearMonth.of(year, month);
        int lastDay = ym.lengthOfMonth();
        int count = 0;

        for (int i = day + 1; i <= lastDay; i++) {
            ScheduleVO vo = new ScheduleVO();
            vo.setSday("--일정 없음");
            date_list.add(vo);
            count++;

            if (date_list.size() == date_len) {
                break;
            }
        }

        if (count < date_len - date_list.size()) {
            // 달이 바뀌거나 연도가 바뀌어서 일자가 다 못 들어간거임
            // 중간 중간 값이 없을 수도 있으니 available로
        }

        return date_list;
    }
}

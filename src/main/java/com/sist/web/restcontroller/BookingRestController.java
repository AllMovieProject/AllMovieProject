package com.sist.web.restcontroller;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.service.BookingService;
import com.sist.web.vo.MovieVO;
import com.sist.web.vo.ScheduleVO;
import com.sist.web.vo.TheaterVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bService;

    private Map<String, Object> getDateList1(Integer year, Integer month, int page) {

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

    @GetMapping("/booking/data_vue/")
    public ResponseEntity<Map<String, Object>> booking_datas(@RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "movie", required = false) int movie,
            @RequestParam(name = "region", required = false) int region,
            @RequestParam(name = "theater", required = false) String theater) {
        Map<String, Object> map = new HashMap<>();

        try {
            map.putAll(getDateList(movie, theater));
            map.putAll(getMovieList(date, region, theater));
            map.putAll(getTheaterList(date, movie, region));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    private Map<String, Object> getDateList(int movie, String theater) {
        Map<String, Object> map = new HashMap<>();
        
        map.put("movie", movie);
        map.put("theater", theater);
        List<ScheduleVO> date_list = bService.dynamicDateListData(map);
        
        map = new HashMap<>();
        map.put("date_list", date_list);
        return map;
    }
    
    private Map<String, Object> getMovieList(String date, int region, String theater) {
        Map<String, Object> map = new HashMap<>();
        
        map.put("date", date);
        map.put("region", region);
        map.put("theater", theater);
        List<MovieVO> movie_list = bService.dynamicMovieListData(map);
        
        map = new HashMap<>();
        map.put("movie_list", movie_list);
        
        return map;
    }
    
    private Map<String, Object> getTheaterList(String date, int movie, int region) {
        Map<String, Object> map = new HashMap<>();
        
        List<TheaterVO> region_list = bService.theaterRegionListData();
        
        map.put("date", date);
        map.put("movie", movie);
        map.put("region", region);
        List<TheaterVO> theater_list = bService.dynamicTheaterListData(map);
        
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
}

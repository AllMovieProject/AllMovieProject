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
import com.sist.web.vo.TheaterVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bService;

    @GetMapping("booking/date_list/")
    public ResponseEntity<Map<String, Object>> booking_date_list(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month, @RequestParam("page") Integer page) {

        Map<String, Object> map = new HashMap<>();

        try {
            map = getDateList1(year, month, page);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

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

    @GetMapping("booking/movie_list/")
    public ResponseEntity<List<MovieVO>> movie_list() {
        List<MovieVO> list = new ArrayList<>();

        try {
            list = bService.bookingAvailableMovieListData();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("booking/region_list/")
    public ResponseEntity<List<TheaterVO>> region_list() {
        List<TheaterVO> list = new ArrayList<>();

        try {
            list = bService.theaterRegionListData();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("booking/theater_list/")
    public ResponseEntity<List<TheaterVO>> theater_list(@RequestParam("no") int no) {
        List<TheaterVO> list = new ArrayList<>();

        try {
            list = bService.theaterListData(no);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/booking/data_vue/")
    public ResponseEntity<Map<String, Object>> booking_datas(@RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "movie", required = false) String movie,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "theater", required = false) String theater) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 체크박스 느낌으로 동적쿼리 사용 복잡하게 생각하지 말기 
            map.putAll(getDateList(movie, theater));
            map.putAll(getMovieList(date, theater));
            map.putAll(getTheaterList(date, movie, region));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    private Map<String, Object> getDateList(String date, String movie) {
        Map<String, Object> map = new HashMap<>();
        // 날짜도 많이 가져오지 말고 맨 마지막 날짜까지
        map.put("date", "date");
        return map;
    }
    
    private Map<String, Object> getMovieList(String date, String theater) {
        Map<String, Object> map = new HashMap<>();
        map.put("movie", "movie");
        return map;
    }
    
    private Map<String, Object> getTheaterList(String date, String movie, String region) {
        Map<String, Object> map = new HashMap<>();
        
        List<TheaterVO> region_list = bService.theaterRegionListData();
        
        map.put("date", date);
        map.put("movie", movie);
        map.put("region", region);
        List<TheaterVO> theater_list = bService.dynamicTheaterListData(map);
        
        for (TheaterVO vo:region_list) {
        	vo.setCount(0);
        	
            for (TheaterVO tvo:theater_list) {
            	if(vo.getRegion_no() == tvo.getRegion_no()) {
            		vo.setCount(vo.getCount() + 1);;
            	}
            }
        }

        map = new HashMap<>();
        map.put("region_list", region_list);
        
        if (region != null) {
            map.put("theater_list", theater_list);
        }
        
        return map;
    }
}

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
            map = getDateList(year, month, page);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, Object> getDateList(Integer year, Integer month, int page) {

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
}

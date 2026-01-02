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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingRestController {

    @GetMapping("booking/date_list/")
    public ResponseEntity<Map<String, Object>> booking_date_list(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam("page") Integer page) {
        
        Map<String, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        try {
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
            final int PAGE_SIZE = 13;
            int startDay = dayNow + (page - 1) * PAGE_SIZE;
            int endDay = dayNow + page * PAGE_SIZE;
            
            if (endDay <= lastDay) {
                for (int i = startDay; i < endDay; i++) {
                    list.add(i);
                }
            } else {
                for (int i = startDay; i < lastDay; i++) {
                    list.add(i);
                }
                
                if (month == 12) {
                    
                } else {
                    month++;
                    ym = YearMonth.of(year, month);
                    lastDay = ym.lengthOfMonth();
                }
            }

            map.put("list", list);
            map.put("year", year);
            map.put("month", month);
            map.put("page", page);

        } catch (Exception e) {
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

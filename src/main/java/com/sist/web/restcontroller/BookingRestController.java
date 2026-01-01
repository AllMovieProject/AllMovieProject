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
            @RequestParam("year") int year, @RequestParam("month") int month) {
        // 초기값 store에서 받아오지 말고 여기서 null 처리 및 pagination 처럼 쭉 15일씩 날짜 보이게 (달력 클릭시 해당날짜 스케줄 보여주는 건 후순위
        Map<String, Object> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        
        try {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            String today = df.format(date);
            StringTokenizer st = new StringTokenizer(today, "-");
            
            int y = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());

            YearMonth ym = YearMonth.of(year, month);
            int lastDay = ym.lengthOfMonth();
            
            for (int i = day; i <= lastDay; i++) {
                list.add(i);
            }
            
            map.put("list", list);
        } catch (Exception e) {
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

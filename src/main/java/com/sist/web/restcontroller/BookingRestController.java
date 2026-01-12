package com.sist.web.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.BookingRequestDTO;
import com.sist.web.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bService;

    @PostMapping("booking/data/")
    public ResponseEntity<Map<String, Object>> bookingListData(@RequestBody BookingRequestDTO dto) {
        Map<String, Object> map = new HashMap<>();
        
        try {
            map = bService.bookingListData(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}

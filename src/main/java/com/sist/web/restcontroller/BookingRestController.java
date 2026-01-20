package com.sist.web.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.BookingRequestDTO;
import com.sist.web.dto.SeatBookingDTO;
import com.sist.web.service.BookingService;
import com.sist.web.service.SeatService;
import com.sist.web.vo.ScheduleVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bService;
    private final SeatService sService;

    @PostMapping("/booking/data/")
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
    
    @PostMapping("/seat/data")
    public ResponseEntity<Map<String, Object>> seatListData(@RequestBody ScheduleVO vo) {
        Map<String, Object> map = new HashMap<>();
        
        try {
        	map = sService.seatListData(vo.getSchedule_id());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @PostMapping("/seat/booking_info")
    public ResponseEntity<Map<String, Object>> bookingInfoData(@RequestBody ScheduleVO vo) {
        Map<String, Object> map = new HashMap<>();
        
        try {
            map = sService.bookingInfoData(vo.getSchedule_id());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @PostMapping("/seat/booking_seat")
    public void bookingSeat(@RequestBody SeatBookingDTO dto) {
        try {
            sService.seatBooking(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping("/seat/booking_cancel")
    public void bookingSeatCancel(@RequestBody SeatBookingDTO dto) {
        try {
            sService.seatBookingCancel(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping("/booking/complete")
    public void bookingComplete(@RequestBody SeatBookingDTO dto) {
        try {
            sService.bookingComplete(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

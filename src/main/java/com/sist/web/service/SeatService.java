package com.sist.web.service;

import java.util.Map;

import com.sist.web.dto.SeatBookingDTO;

public interface SeatService {
	public Map<String, Object> seatListData(int id);
	public Map<String, Object> bookingInfoData(int id);
	public Integer seatBooking(SeatBookingDTO dto);
}

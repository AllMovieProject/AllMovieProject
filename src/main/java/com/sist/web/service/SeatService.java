package com.sist.web.service;

import java.util.Map;

import com.sist.web.dto.SeatBookingDTO;
import com.sist.web.vo.ScheduleSeatVO;

public interface SeatService {
	public Map<String, Object> seatListData(int id);
	public Map<String, Object> bookingInfoData(int id);
	public String seatValidation(ScheduleSeatVO vo);
	public void seatBooking(SeatBookingDTO dto);
    public void seatBookingCancel(SeatBookingDTO dto);
    public void bookingComplete(SeatBookingDTO dto);
}

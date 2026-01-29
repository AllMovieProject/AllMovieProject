package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BookingVO {
    private Date booking_date;
	private int schedule_id, cancel_flag;
	private String booking_id, member_id, merchant_uid;
	
	private ScheduleVO svo = new ScheduleVO();
	private MovieVO mvo = new MovieVO();
	private TheaterVO tvo = new TheaterVO();
	private ScreenVO scvo = new ScreenVO();
    private BookingSeatVO bsvo = new BookingSeatVO();
}

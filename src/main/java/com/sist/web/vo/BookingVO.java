package com.sist.web.vo;

import lombok.Data;

@Data
public class BookingVO {
	private int schedule_id, cancel_flag;
	private String booking_id, member_id;
	
	private ScheduleVO svo = new ScheduleVO();
	private MovieVO mvo = new MovieVO();
	private TheaterVO tvo = new TheaterVO();
	private ScreenVO scvo = new ScreenVO();
    private BookingSeatVO bsvo = new BookingSeatVO();
}

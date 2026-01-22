package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ScheduleVO {
	private int schedule_id, movie_id, screen_id, available_flag;
	private Date start_time, end_time;
	private String sday;
	
	// booking schedule list data
	private int available_count, total_count; // seat
	private String starttime, endtime; // runtime
	
	private MovieVO mvo = new MovieVO();
	private TheaterVO tvo = new TheaterVO();
	private ScreenVO scvo = new ScreenVO();
	private ScheduleSeatVO ssvo = new ScheduleSeatVO();
}

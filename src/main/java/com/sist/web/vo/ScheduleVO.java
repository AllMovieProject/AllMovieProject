package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ScheduleVO {
	private int schedule_id, movie_id, screen_id, available_count, total_count;
	private Date start_time, end_time;
	private String sday, schedule_time, starttime, endtime;
	
	private MovieVO mvo = new MovieVO();
	private TheaterVO tvo = new TheaterVO();
	private ScreenVO scvo = new ScreenVO();
	private ScheduleSeatVO ssvo = new ScheduleSeatVO();
}
